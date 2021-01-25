package count_game.game.processors.ChuTiInner.base;

import count_game.game.processors.base.CountGameContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChuTiUtil {

    public static final String PLUS = "+";
    public static final String MINORS = "-";

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

    public static String genIn20(CountGameContext cxt) {
        Random r = new Random();
        String returnStr;
        int correct = 0;
        do {
            int first = genInt(r, 11, 20);
            String firstSymbol = genInt(r, 1, 10) % 2 == 0 ? PLUS : MINORS;
            String secondSymbol = firstSymbol.equals(PLUS) ? MINORS : PLUS;
            int second = genInt(r, 1, 10);
            int third = genInt(r, 1, 10);


            if (firstSymbol.equals(PLUS)) {
                correct = first + second - third;
                returnStr = first + PLUS + second + MINORS + third + "=";
            } else {
                correct = first - second + third;
                returnStr = first + MINORS + second + PLUS + third + "=";
            }
        } while (correct <= 0);
        cxt.setCorrectAnswer(correct);
        return returnStr;
    }
}
