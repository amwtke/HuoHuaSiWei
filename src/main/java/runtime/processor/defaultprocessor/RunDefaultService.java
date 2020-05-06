package runtime.processor.defaultprocessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RunDefaultService extends DefaultProcessorService {
    public <R, C extends DefaultProcessorContext<R>, P extends DefaultProcessor<R, C>>
    DefaultProcessorResult<R> run(List<P> processors, C defaultContext) {
        return runProcessors(processors, defaultContext);
    }
}
