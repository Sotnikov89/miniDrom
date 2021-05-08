package ru.drom.dao;

import ru.drom.model.Advert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DispatchDiapasonDao {

    private final Map<Function<Map<String, Integer>, Boolean>, Function<Map<String, Integer>, List<Advert>>> dispat = new HashMap<>();

    private String sql = "select advert from Advert advert "
            + "join fetch advert.user user join fetch user.city city join fetch advert.model model "
            + "join fetch model.make make join fetch advert.typeBody typeBody "
            + "where model.id = :model and make.id = :make and typeBody.id = :type and advert.sold = false";

    public DispatchDiapasonDao init() {
        this.dispat.put(
                map -> map.get("mileage") == 0 && map.get("price") == 0 && map.get("photo") != 0,
                map -> HibernateConnect.instOf().sessionMethodsWithReturn(session -> session.createQuery(sql + " and advert.photoId not in (0)")
                        .setParameter("model", map.get("model")).setParameter("make", map.get("make")).setParameter("type", map.get("type"))
                        .list())
                );
        this.dispat.put(
                map -> map.get("mileage") == 0 && map.get("price") != 0 && map.get("photo") != 0,
                map -> HibernateConnect.instOf().sessionMethodsWithReturn(session -> session.createQuery(sql + " and advert.photoId not in (0) and advert.price <= :price")
                        .setParameter("model", map.get("model")).setParameter("make", map.get("make"))
                        .setParameter("type", map.get("type")).setParameter("price", map.get("price"))
                        .list())
        );
        this.dispat.put(
                map -> map.get("mileage") != 0 && map.get("price") != 0 && map.get("photo") != 0,
                map -> HibernateConnect.instOf().sessionMethodsWithReturn(session -> session.createQuery(sql + " and advert.photoId not in (0) and advert.price <= :price and advert.mileage <= :mileage")
                        .setParameter("model", map.get("model")).setParameter("make", map.get("make")).setParameter("type", map.get("type"))
                        .setParameter("price", map.get("price")).setParameter("mileage", map.get("mileage"))
                        .list())
        );
        this.dispat.put(
                map -> map.get("mileage") != 0 && map.get("price") == 0 && map.get("photo") == 0,
                map -> HibernateConnect.instOf().sessionMethodsWithReturn(session -> session.createQuery(sql + " and advert.mileage <= :mileage")
                        .setParameter("model", map.get("model")).setParameter("make", map.get("make"))
                        .setParameter("type", map.get("type")).setParameter("mileage", map.get("mileage"))
                        .list())
        );
        this.dispat.put(
                map -> map.get("mileage") == 0 && map.get("price") == 0 && map.get("photo") == 0,
                map -> HibernateConnect.instOf().sessionMethodsWithReturn(session -> session.createQuery(sql)
                        .setParameter("model", map.get("model")).setParameter("make", map.get("make")).setParameter("type", map.get("type"))
                        .list())
        );
        this.dispat.put(
                map -> map.get("mileage") == 0 && map.get("price") != 0 && map.get("photo") == 0,
                map -> HibernateConnect.instOf().sessionMethodsWithReturn(session -> session.createQuery(sql + " and advert.price <= :price")
                        .setParameter("model", map.get("model")).setParameter("make", map.get("make"))
                        .setParameter("type", map.get("type")).setParameter("price", map.get("price"))
                        .list())
        );
        this.dispat.put(
                map -> map.get("mileage") != 0 && map.get("price") == 0 && map.get("photo") != 0,
                map -> HibernateConnect.instOf().sessionMethodsWithReturn(session -> session.createQuery(sql + " and advert.photoId not in (0) and advert.mileage <= :mileage")
                        .setParameter("model", map.get("model")).setParameter("make", map.get("make"))
                        .setParameter("type", map.get("type")).setParameter("mileage", map.get("mileage"))
                        .list())
        );
        return this;
    }

    public List<Advert> filter(Map<String, Integer> map) {
        for (Function<Map<String, Integer>, Boolean> predict : this.dispat.keySet()) {
            if (predict.apply(map)) {
                return this.dispat.get(predict).apply(map);
            }
        }
        throw new IllegalStateException("Could not found a filter config for this map");
    }
}
