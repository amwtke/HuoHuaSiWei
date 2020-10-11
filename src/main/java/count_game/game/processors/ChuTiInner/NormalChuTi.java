package count_game.game.processors.ChuTiInner;

import count_game.game.processors.ChuTiInner.base.ChuTiFunction;
import count_game.game.processors.ChuTiInner.base.ChuTiType;
import count_game.game.processors.base.CountGameContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

import static count_game.game.processors.ChuTiInner.base.ChuTiUtil.genInt;
import static java.util.Objects.isNull;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NormalChuTi implements ChuTiFunction {
    @Override
    public String print(CountGameContext cxt) {
        return chuTi(cxt);
    }

    @Override
    public ChuTiType getType() {
        return ChuTiType.NORMAL;
    }

    @Override
    public void setCorrect(CountGameContext cxt) {
        log.debug("no need!");
    }

    private String chuTi(CountGameContext ctx) {
        Random r = new Random();
        int first, second;
        do {
            first = genInt(r, 0, ctx.getUpperBound());
            second = genInt(r, 0, ctx.getUpperBound());
        } while (first == second);
        if (first < second) {
            return print(ctx, second, first);
        } else {
            return print(ctx, first, second);
        }
    }

    private String print(CountGameContext ctx, int first, int second) {
        Random r = new Random();
        boolean flag = Math.abs(r.nextInt()) % 2 == 0;
        if (flag) {
            ctx.setCorrectAnswer(first + second);
            return first + "+" + second + "=";
        } else {
            ctx.setCorrectAnswer(first - second);
            return first + "-" + second + "=";
        }
    }
}
