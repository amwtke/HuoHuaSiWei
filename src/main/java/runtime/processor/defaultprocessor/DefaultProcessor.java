package runtime.processor.defaultprocessor;

import runtime.processor.annotation.SortOrder;
import runtime.processor.baseprocessor.Processor;

public interface DefaultProcessor<R, T extends DefaultProcessorContext<R>> extends Processor<T> {

    @Override
    default int getPriority() {
        return this.getClass().getAnnotation(SortOrder.class).value();
    }
}
