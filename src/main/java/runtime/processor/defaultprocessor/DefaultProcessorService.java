package runtime.processor.defaultprocessor;

import runtime.processor.baseservice.AbstractService;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public abstract class DefaultProcessorService extends AbstractService {
    public <R, C extends DefaultProcessorContext<R>, P extends DefaultProcessor<R, C>> DefaultProcessorResult<R> runProcessors(
            List<P> processors, C defaultContext) {
        if (CollectionUtils.isEmpty(processors) || defaultContext == null) {
            return null;
        }
        try {
            innerRun(processors, defaultContext);
        } catch (Exception ex) {
            if (!Objects.isNull(defaultContext.getResult())) {
                defaultContext.getResult().setSuccessful(false);
            }
        }
        printExceptionsInContext(defaultContext);
        return defaultContext.getResult();
    }

    public <R, C extends DefaultProcessorContext<R>, P extends DefaultProcessor<R, C>>
    CompletableFuture<DefaultProcessorResult> runProcessorsAsync(
            List<P> processors, C defaultContext, Executor executor) {
        if (processors == null || processors.isEmpty() || defaultContext == null || executor == null) {
            return null;
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                innerRun(processors, defaultContext);
                return defaultContext.getResult();
            } catch (Exception ex) {
                printExceptionsInContext(defaultContext);
                return defaultContext.getResult();
            }
        }, executor);
    }
}
