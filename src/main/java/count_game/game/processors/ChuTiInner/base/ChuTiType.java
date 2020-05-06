package count_game.game.processors.ChuTiInner.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ChuTiType {
    NORMAL(0, "10以内加减法"),
    COU_SHI(1, "凑十加法运算"),
    PO_SHI(2, "破十减法运算"),
    PO_COU_SHI(3, "破凑十混合运算");

    private static final Map<Integer, ChuTiType> MAP = new ConcurrentHashMap<>();

    static {
        for (ChuTiType t : ChuTiType.values()) {
            MAP.putIfAbsent(t.no, t);
        }
    }

    ChuTiType(int no, String description) {
        this.no = no;
        this.description = description;
    }

    private final int no;
    private final String description;

    public static ChuTiType getByNo(Integer no) {
        return MAP.get(no);
    }

    public static int totalTypeCount() {
        return MAP.values().size();
    }

    public String getDescription() {
        return this.description;
    }
}
