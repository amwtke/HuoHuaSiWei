package count_game.game.processors.ChuTiInner.base;

import count_game.game.processors.base.CountGameContext;

public interface ChuTiFunction {
    String print(CountGameContext cxt);

    ChuTiType getType();

    void setCorrect(CountGameContext cxt);
}
