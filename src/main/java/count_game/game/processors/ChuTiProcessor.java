package count_game.game.processors;

import count_game.game.processors.ChuTiInner.ChuTiManager;
import count_game.game.processors.ChuTiInner.base.ChuTiType;
import count_game.game.processors.base.CountGameContext;
import count_game.game.processors.base.CountGameProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import runtime.processor.annotation.SortOrder;
import runtime.processor.baseprocessor.ProcessorException;

import static count_game.game.processors.base.GameProcessorPriority.CHU_TI;
import static java.util.Objects.isNull;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SortOrder(CHU_TI)
public class ChuTiProcessor implements CountGameProcessor {
    private final ChuTiManager chuTiManager;

    @Override
    public void process(CountGameContext ctx) throws ProcessorException {
        if (isNull(ctx.getChuTiType())) {
            System.out.println(chuTiManager.getChuTiFunction(ChuTiType.NORMAL).print(ctx));
        } else {

            System.out.println(chuTiManager.getChuTiFunction(ctx.getChuTiType()).print(ctx));
        }
    }
}
