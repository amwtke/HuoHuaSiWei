package count_game.game;

import count_game.CountPracticeInputObject;
import count_game.game.processors.base.CountGameContext;
import count_game.game.processors.base.CountGameProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runtime.processor.defaultprocessor.DefaultProcessorResult;
import runtime.processor.defaultprocessor.DefaultProcessorService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CountGameService {
    private final List<CountGameProcessor> processors;
    private final DefaultProcessorService defaultProcessorService;

    public void run(CountPracticeInputObject countPracticeInputObject) {
        CountGameContext countGameContext = new CountGameContext();
        countGameContext.setUpperBound(countPracticeInputObject.getUpperBound());
        countGameContext.setScanner(countPracticeInputObject.getScanner());
        countGameContext.setChuTiType(countPracticeInputObject.getChuTiType());
        DefaultProcessorResult<Boolean> objectDefaultProcessorResult
                = defaultProcessorService.runProcessors(processors, countGameContext);
        if (objectDefaultProcessorResult.getResult()) {
            countPracticeInputObject.setWanChenShu(countPracticeInputObject.getWanChenShu() + 1);
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong!");
        }
        System.out.println("还剩：" + (countPracticeInputObject.getTotalQuestion() + 1 - countPracticeInputObject.getWanChenShu()) + "个.");
    }
}
