package runtime.processor.defaultprocessor;

import runtime.processor.baseprocessor.ProcessorContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class DefaultProcessorContext<R> extends ProcessorContext {
    private DefaultProcessorResult<R> result;

    public DefaultProcessorContext() {
        super();
        this.setResult(new DefaultProcessorResult<>());
        this.getResult().setSuccessful(true);
        this.getResult().setContext(this);
        this.setFinished(false);
    }
}
