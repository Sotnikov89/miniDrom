package ru.drom.controller;

import ru.drom.dto.DTOAdvert;
import ru.drom.service.basicImpl.DefaultServiceAdvert;
import ru.drom.transport.ImplAdvertMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DispatchDiapasonServ {

    private final Map<Function<Map<String, Integer>, Boolean>, Function<Map<String, Integer>, List<DTOAdvert>>> dispat = new HashMap<>();

    public DispatchDiapasonServ init() {
        this.dispat.put(
                map -> map.get("getAll") != 0,
                map -> new ImplAdvertMapper().advertsToDtoAdverts(DefaultServiceAdvert.instOf().findAllActive())
        );
        this.dispat.put(
                map -> map.get("getByUser") != 0,
                map -> new ImplAdvertMapper().advertsToDtoAdverts(DefaultServiceAdvert.instOf().findAllByUserId(map.get("getByUser")))
        );
        this.dispat.put(
                map -> map.get("getByToday") != 0,
                map -> new ImplAdvertMapper().advertsToDtoAdverts(DefaultServiceAdvert.instOf().findAllThisDay())
        );
        this.dispat.put(
                map -> map.get("make") != 0,
                map -> new ImplAdvertMapper().advertsToDtoAdverts(DefaultServiceAdvert.instOf().findAllByFilter(map))
        );
        return this;
    }

    public List<DTOAdvert> filter(Map<String, Integer> map) {
        for (Function<Map<String, Integer>, Boolean> predict : this.dispat.keySet()) {
            if (predict.apply(map)) {
                return this.dispat.get(predict).apply(map);
            }
        }
        throw new IllegalStateException("Could not found a query for this param");
    }
}
