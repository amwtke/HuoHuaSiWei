package runtime.processor.baseprocessor;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public abstract class ProcessorContext {
    private boolean isFinished;
    /**
     * @key className or className + data primary key
     * @Value Exception
     */
    private Map<String, Throwable> exceptions = new ConcurrentHashMap<>();
    private List<Integer> ignoreProcessorList;
    private String errorLogId;

    private String sessionId;

    public void logError(String key, Throwable exception) {
        exceptions.putIfAbsent(key, exception);
    }
}
