package runtime.processor.defaultprocessor;

import runtime.processor.baseprocessor.ProcessorResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DefaultProcessorResult<R> extends ProcessorResult<R> {
    private DefaultProcessorContext<R> context;
}
