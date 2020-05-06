package runtime.processor.baseprocessor;

import lombok.Getter;

@Getter
public class ProcessorException extends Exception {
    private static final long serialVersionUID = -6769240661882118847L;
    private static final String ERROR_HEADER = "ProcessorException->";

    private transient Object errorObject;

    public ProcessorException(String error) {
        super(ERROR_HEADER + error);
    }

    public ProcessorException(String errorMsg, Object errorObject) {
        super(ERROR_HEADER +
                errorMsg + "->" +
                "error class->" + errorObject.getClass().getName() +
                " -> object msg:" + errorObject.toString());
        this.errorObject = errorObject;
    }
}
