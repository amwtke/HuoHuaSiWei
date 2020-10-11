package count_game;

import count_game.game.CountGameService;
import count_game.game.processors.ChuTiInner.ChuTiManager;
import count_game.game.processors.ChuTiInner.base.ChuTiType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CountPractice {
    private static CountGameService countGameService;
    private final ChuTiManager chuTiManager;

    public static void main(String[] args) {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext("count_game.game", "runtime");
        countGameService = ctx.getBean(CountGameService.class);
        if (isNull(countGameService)) {
            log.error("Can't find bean!");
            return;
        }

        System.out.println("Hello Chris!");
        Scanner sc = new Scanner(System.in);
        CountPracticeInputObject countPracticeInputObject = makeInput(sc);
        System.out.println(getInstructions(countPracticeInputObject));
        String holder;
        System.out.println("打q退出，点击任意数字继续继续答题！");
        while (!(holder = sc.next()).equals("q")) {
            if (holder.equals("20")) {
                run20(countPracticeInputObject, sc);
                continue;
            }
            countPracticeInputObject = runNormal(sc, holder);
        }
        System.out.println("Bye!");
    }

    private static void run20(CountPracticeInputObject countPracticeInputObject, Scanner sc) {
        countPracticeInputObject = makeInput(sc);
        countPracticeInputObject.setTotalQuestion(20);
        while (countPracticeInputObject.getFinishedCount() < countPracticeInputObject.getTotalQuestion()) {
            countGameService.run_20(countPracticeInputObject);
        }
    }

    private static CountPracticeInputObject runNormal(Scanner sc, String holder) {
        CountPracticeInputObject countPracticeInputObject;
        countPracticeInputObject = makeInput(sc);
        setChutiType(holder, countPracticeInputObject);
        Long begin = System.currentTimeMillis();
        while (countPracticeInputObject.getFinishedCount() < countPracticeInputObject.getTotalQuestion()) {
            countGameService.run(countPracticeInputObject);
        }
        System.out.println("共用时：" + ((System.currentTimeMillis() - begin) / 1000) + "秒.");
        System.out.println("打q退出，点击任意数字继续继续答题！");
        return countPracticeInputObject;
    }

    private static void setChutiType(String holder, CountPracticeInputObject countPracticeInputObject) {
        char firstChar = holder.charAt(0);
        int chuTiNo = Math.abs((int) firstChar % ChuTiType.totalTypeCount());
        countPracticeInputObject.setChuTiType(ChuTiType.getByNo(chuTiNo));
        System.out.println("现在做:" + countPracticeInputObject.getChuTiType().getDescription() + ".");
    }

    private static CountPracticeInputObject makeInput(Scanner sc) {
        return CountPracticeInputObject.builder()
                .scanner(sc)
                .totalQuestion(14)
                .upperBound(11)
                .finishedCount(0)
                .build();
    }

    private static String getInstructions(CountPracticeInputObject countPracticeInputObject) {
        return "请完成：" + (countPracticeInputObject.getTotalQuestion() + 1) + "道题目！";
    }
}
