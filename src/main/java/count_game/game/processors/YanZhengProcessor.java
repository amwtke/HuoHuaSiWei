package count_game.game.processors;

import count_game.game.processors.base.CountGameContext;
import count_game.game.processors.base.CountGameProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import runtime.processor.annotation.SortOrder;
import runtime.processor.baseprocessor.ProcessorException;

import static count_game.game.processors.base.GameProcessorPriority.YAN_ZHENG;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SortOrder(YAN_ZHENG)
public class YanZhengProcessor implements CountGameProcessor {

    @Override
    public void process(CountGameContext ctx) throws ProcessorException {
        if (ctx.getDaTi() == ctx.getCorrectAnswer()) {
            ctx.getResult().setResult(true);
        } else {
            ctx.getResult().setResult(false);
        }
    }
}
