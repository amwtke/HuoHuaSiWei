package runtime.processor.baseprocessor;

public interface Processor<T extends ProcessorContext> {
    void process(T ctx) throws ProcessorException;

    int getPriority();
}
