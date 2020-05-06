package count_game.game.processors.base;

import count_game.game.processors.ChuTiInner.base.ChuTiType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import runtime.processor.defaultprocessor.DefaultProcessorContext;

import java.util.Scanner;

@Data
@EqualsAndHashCode(callSuper = false)
public class CountGameContext extends DefaultProcessorContext<Boolean> {
    private Scanner scanner;
    private int upperBound;
    private int lowerBound = 0;

    private ChuTiType chuTiType;

    private int correctAnswer;
    private int daTi;

    public CountGameContext() {
        super();
        getResult().setResult(true);
    }
}
