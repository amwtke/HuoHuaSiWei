package count_game.game.processors;

import count_game.game.processors.base.CountGameContext;
import count_game.game.processors.base.CountGameProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import runtime.processor.baseprocessor.ProcessorException;

import static count_game.game.processors.base.GameProcessorPriority.ZUO_TI;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Order(ZUO_TI)
public class ZuoTiProcessor implements CountGameProcessor {
    @Override
    public void process(CountGameContext ctx) throws ProcessorException {
        int holder;
        while (true) {
            String next = ctx.getScanner().next();
            if (isNumeric(next)) {
                holder = Integer.parseInt(next);
                break;
            } else {
                System.out.println("非法字符，继续输入数字！");
            }
        }
        ctx.setDaTi(holder);
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
