package count_game.game.processors.ChuTiInner.chutifunctions;

import count_game.game.processors.ChuTiInner.base.ChuTiFunction;
import count_game.game.processors.ChuTiInner.base.ChuTiType;
import count_game.game.processors.base.CountGameContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

import static count_game.game.processors.ChuTiInner.base.ChuTiUtil.genCouShi;
import static count_game.game.processors.ChuTiInner.base.ChuTiUtil.genPoShi;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PoCouShiMixFunction implements ChuTiFunction {
    @Override
    public String print(CountGameContext cxt) {
        Random r = new Random();
        int randomInt = Math.abs(r.nextInt());
        if (randomInt % 2 == 0) {
            return genPoShi(cxt);
        } else {
            return genCouShi(cxt);
        }
    }

    @Override
    public ChuTiType getType() {
        return ChuTiType.PO_COU_SHI;
    }

    @Override
    public void setCorrect(CountGameContext cxt) {
        log.debug("hehe");
    }
}
