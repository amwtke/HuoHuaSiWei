package count_game.game.processors.ChuTiInner;

import count_game.game.processors.ChuTiInner.base.ChuTiFunction;
import count_game.game.processors.ChuTiInner.base.ChuTiType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChuTiManager {
    private final List<ChuTiFunction> list;
    private static Map<ChuTiType, ChuTiFunction> MAP = new ConcurrentHashMap<>();

    @PostConstruct
    public void setUpMap() {
        if (isEmpty(list)) {
            log.error("No Chu ti function found!");
        }
        list.forEach(v -> MAP.putIfAbsent(v.getType(), v));
    }

    public ChuTiFunction getChuTiFunction(ChuTiType chuTiType) {
        if (isNull(chuTiType)) {
            return MAP.get(ChuTiType.NORMAL);
        }
        return MAP.get(chuTiType);
    }

    public int getFunctionCount() {
        return MAP.values().size();
    }
}
