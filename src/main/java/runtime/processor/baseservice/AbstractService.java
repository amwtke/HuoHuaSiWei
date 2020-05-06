package runtime.processor.baseservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import runtime.processor.baseprocessor.Processor;
import runtime.processor.baseprocessor.ProcessorContext;
import runtime.processor.baseprocessor.ProcessorException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;
import static runtime.processor.baseprocessor.CommonProcessorConst.PROCESSOR_IGNORED;

@Slf4j
@SuppressWarnings("PMD.ConsecutiveLiteralAppends")
public abstract class AbstractService {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    protected String getServiceName() {
        return this.getClass().getName();
    }

    protected <C extends ProcessorContext, P extends Processor<C>> void innerRun(
            List<P> processors, C processContext) throws ProcessorException {
        if (processContext == null) {
            log.error(getServiceName() + "->ProcessContext is null!");
            return;
        }
        for (P processor : filterProcessors(processors, processContext)) {
            if (processor.getPriority() == PROCESSOR_IGNORED) {
                continue;
            }
            try {
                processor.process(processContext);
                if (processContext.isFinished()) {
                    break;
                }
            } catch (ProcessorException | RuntimeException ex) {
                processContext.getExceptions().put(getServiceName(), ex);
                throw ex;
            }
        }
    }

    protected <P extends Processor<C>, C extends ProcessorContext> List<P> filterProcessors(List<P> processors, C processContext) {
        List<Integer> ignoreProcessorList = processContext.getIgnoreProcessorList();
        if (CollectionUtils.isEmpty(ignoreProcessorList)) {
            return processors;
        }
        return processors.stream()
                .filter(p -> !ignoreProcessorList.contains(p.getPriority()))
                .collect(Collectors.toList());
    }

    protected void printExceptionsInContext(ProcessorContext processContext) {
        if (processContext.getExceptions().size() > 0) {
            StringBuilder sb = new StringBuilder(140);
            sb.append("SESSION_ID:")
                    .append(processContext.getSessionId())
                    .append("->")
                    .append("===>begin print exceptions for service ->>>")
                    .append(getServiceName())
                    .append(LINE_SEPARATOR);
            processContext.getExceptions().forEach((k, v) -> {
                sb.append("-------------------------------------------------------------------------------------------------")
                        .append(LINE_SEPARATOR)
                        .append("MSG_ERROR_LOG_ID is:")
                        .append(isEmpty(processContext.getErrorLogId()) ? "NOT SPECIFIED. " : processContext.getErrorLogId())
                        .append(LINE_SEPARATOR)
                        .append(String.format("Processor class->%s, %n Error msg->%s. %n", k, v.toString()));
                if (v.getStackTrace() != null && v.getStackTrace().length > 0) {
                    for (int i = 0; i < v.getStackTrace().length; i++) {
                        sb.append(v.getStackTrace()[i].toString()).append('\n');
                    }
                }
            });
            sb.append("=======================end print exceptions====================== \n");
            log.error(sb.toString());
        }
    }
}
