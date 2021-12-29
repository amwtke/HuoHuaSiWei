package runtime.processor.defaultprocessor;

import lombok.extern.slf4j.Slf4j;
import runtime.processor.defaultprocessor.DefaultProcessorService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public abstract class AbstractServiceQueueAsync<T> extends DefaultProcessorService {
    private static final int POLL_WAITING_INTERVAL_MINUTES = 10;
    private static final int QUEUE_EXCEPTION_SLEEP_INTERVAL_MILLIS = 10_000;
    private static final int THREAD_POLL_TERMINATION_WAITING_MILLIS = 1_000;

    private final ExecutorService processingThread = initThreadPool();
    private final AtomicBoolean threadToggle = new AtomicBoolean(false);
    private final BlockingQueue<T> blockingQueue = new LinkedBlockingDeque<>();

    protected abstract void consumer(List<T> batchData);

    protected ExecutorService initThreadPool() {
        return Executors.newSingleThreadExecutor(r -> new Thread(r, getServiceName() + "-thread"));
    }

    @PostConstruct
    protected void initThreadWhenServiceUp() {
        startProcessingThread();
    }

    @PreDestroy
    protected void destroyThreadWhenServiceDown() {
        shutDownAggregationThread();
    }

    public void push(T data) {
        if (null != data) {
            try {
                blockingQueue.put(data);
            } catch (InterruptedException e) {
                log.error("Transaction_Service:{}, put exception!", getServiceName());
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    protected void startProcessingThread() {
        threadToggle.set(true);
        processingThread.execute(this::processWrapperMethod);
    }

    protected void processWrapperMethod() {
        log.info("transaction service: {} begin, threadId:{}.", getServiceName(), Thread.currentThread().getId());
        List<T> dataBuffer = new LinkedList<>();
        while (threadToggle.get()) {
            try {
                T queueNode = blockingQueue.poll(POLL_WAITING_INTERVAL_MINUTES, TimeUnit.MINUTES);
                if (Objects.isNull(queueNode)) {
                    log.info("No data in queue!");
                    continue;
                }
                dataBuffer.add(queueNode);
                if (!this.blockingQueue.isEmpty()) {
                    blockingQueue.drainTo(dataBuffer);
                }
                consumer(dataBuffer);
                dataBuffer.clear();
            } catch (Exception e) {
                log.error(e.getMessage());
                try {
                    Thread.sleep(QUEUE_EXCEPTION_SLEEP_INTERVAL_MILLIS);
                } catch (InterruptedException ex) {
                    log.error(ex.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    protected void shutDownAggregationThread() {
        threadToggle.set(false);
        processingThread.shutdown();
        try {
            if (!processingThread.awaitTermination(THREAD_POLL_TERMINATION_WAITING_MILLIS, TimeUnit.MILLISECONDS)) {
                processingThread.shutdownNow();
            }
        } catch (InterruptedException e) {
            processingThread.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("transaction service:{} stopped, threadId:{}.", getServiceName(), Thread.currentThread().getId());
    }
}
