package runtime.processor.defaultprocessor;

import runtime.processor.baseprocessor.Processor;
import org.springframework.core.annotation.Order;

public interface DefaultProcessor<R, T extends DefaultProcessorContext<R>> extends Processor<T> {

    @Override
    default int getPriority() {
        return this.getClass().getAnnotation(Order.class).value();
    }
}
