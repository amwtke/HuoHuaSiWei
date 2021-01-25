package count_game.game;

import count_game.CountPracticeInputObject;
import count_game.game.processors.ChuTiInner.base.ChuTiType;
import count_game.game.processors.base.CountGameContext;
import count_game.game.processors.base.CountGameProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runtime.processor.defaultprocessor.DefaultProcessorResult;
import runtime.processor.defaultprocessor.DefaultProcessorService;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static count_game.game.processors.base.GameProcessorPriority.YAN_ZHENG;
import static count_game.game.processors.base.GameProcessorPriority.ZUO_TI;

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
            countPracticeInputObject.setFinishedCount(countPracticeInputObject.getFinishedCount() + 1);
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong!");
        }
        System.out.println("还剩：" + (countPracticeInputObject.getTotalQuestion() + 1 - countPracticeInputObject.getFinishedCount()) + "个.");
    }

    public void run_20(CountPracticeInputObject countPracticeInputObject) {
        CountGameContext countGameContext = new CountGameContext();
        countGameContext.setIgnoreProcessorList(Arrays.asList(ZUO_TI, YAN_ZHENG));
        countGameContext.setUpperBound(countPracticeInputObject.getUpperBound());
        countGameContext.setChuTiType(getRandomChutiType());
        DefaultProcessorResult<Boolean> objectDefaultProcessorResult
                = defaultProcessorService.runProcessors(processors, countGameContext);
        countPracticeInputObject.setFinishedCount(countPracticeInputObject.getFinishedCount() + 1);
    }

    public void run_in_20(CountPracticeInputObject countPracticeInputObject) {
        CountGameContext countGameContext = new CountGameContext();
        countGameContext.setIgnoreProcessorList(Arrays.asList(ZUO_TI, YAN_ZHENG));
        countGameContext.setUpperBound(countPracticeInputObject.getUpperBound());
        countGameContext.setChuTiType(ChuTiType.IN_20);
        DefaultProcessorResult<Boolean> objectDefaultProcessorResult
                = defaultProcessorService.runProcessors(processors, countGameContext);
        countPracticeInputObject.setFinishedCount(countPracticeInputObject.getFinishedCount() + 1);
    }

    private ChuTiType getRandomChutiType() {
        Random random = new Random();
        int index = Math.abs(random.nextInt()) % ChuTiType.totalTypeCount();
        return ChuTiType.getByNo(index);
    }
}
