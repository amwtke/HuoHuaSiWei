package count_game;

import count_game.game.processors.ChuTiInner.base.ChuTiType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Scanner;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountPracticeInputObject {
    private Scanner scanner;
    private int upperBound;
    private int wanChenShu;
    private int totalQuestion;
    private ChuTiType chuTiType;
}
