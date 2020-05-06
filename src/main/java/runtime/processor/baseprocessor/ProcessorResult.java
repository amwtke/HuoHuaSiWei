package runtime.processor.baseprocessor;

import lombok.Data;

@Data
public abstract class ProcessorResult<T> {
    private boolean isSuccessful;
    private T result;
}
