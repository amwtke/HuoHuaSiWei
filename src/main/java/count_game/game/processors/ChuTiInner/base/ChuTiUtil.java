package count_game.game.processors.ChuTiInner.base;

import count_game.game.processors.base.CountGameContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChuTiUtil {
    public static int genInt(Random random, int maxValue) {
        int ret;
        do {
            ret = Math.abs(random.nextInt() % maxValue);
        } while (ret == 0);
        return ret;
    }

    public static int genInt(Random random, int minValue, int maxValue) {
        int ret;
        do {
            ret = Math.abs(random.nextInt() % maxValue);
        } while (ret <= minValue);
        return ret;
    }

    public static String genCouShi(CountGameContext cxt) {
        Random r = new Random();
        int first;
        int second;
        do {
            first = genInt(r, 1, 10);
            second = genInt(r, 1, 10);
        } while ((first + second) <= 10);
        cxt.setCorrectAnswer(first + second);
        return first + "+" + second + "=";
    }

    public static String genPoShi(CountGameContext cxt) {
        Random r = new Random();
        int first;
        int second;
        do {
            first = genInt(r, 10, 19);
            second = genInt(r, 1, 10);
        } while ((first - second) >= 10);
        cxt.setCorrectAnswer(first - second);
        return first + "-" + second + "=";
    }
}
