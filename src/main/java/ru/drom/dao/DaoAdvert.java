package ru.drom.dao;

import ru.drom.model.Advert;

import java.time.LocalDate;
import java.util.List;

public class DaoAdvert implements Dao<Advert> {

    private final HibernateConnect hibernateConnect;

    private DaoAdvert() {
        hibernateConnect = HibernateConnect.instOf();
    }

    public static DaoAdvert instOf() { return new DaoAdvert(); }

    private String basicSql = "select advert from Advert advert "
            + "join fetch advert.user user join fetch user.city city join fetch advert.model model "
            + "join fetch model.make make join fetch advert.typeBody typeBody ";

    public List<Advert> findAllThisDay() {
        return hibernateConnect.sessionMethodsWithReturn(
                session -> session.createQuery(basicSql + "where advert.sold = :sold and advert.created between :start and :end")
                        .setParameter("end", LocalDate.now().plusDays(1).atStartOfDay()).setParameter("start", LocalDate.now().atStartOfDay()).setParameter("sold", false)
                        .list());
    }

    public List<Advert> findAllByUserId(int userId) {
        return hibernateConnect.sessionMethodsWithReturn(
                session -> session.createQuery(basicSql + "where user.id = :id")
                        .setParameter("id", userId)
                        .list());
    }

    public List<Advert> findAllByFilterOrNull(int make, int model, int type, int mileage, int price, boolean photo) {
        String mmt = "where model.id = :model and make.id = :make and typeBody.id = :type and advert.sold = false";
        if (mileage == 0 & price == 0 & photo) {  // make+model+type - and with photo
            return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(basicSql + mmt + " and advert.photoId not in (0)")
                    .setParameter("model", model).setParameter("make", make).setParameter("type", type)
                    .list());
        }
        if (mileage == 0 & price != 0 & photo) {  // make+model+type - with photo and price
            return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(basicSql + mmt + " and advert.photoId not in (0) and advert.price = :price")
                    .setParameter("model", model).setParameter("make", make).setParameter("type", type).setParameter("price", price)
                    .list());
        }
        if (mileage != 0 & price != 0 & photo) {  // make+model+type - with photo,price and mileage
            return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(basicSql + mmt + " and advert.photoId not in (0) and advert.price = :price and advert.mileage = :mileage")
                    .setParameter("model", model).setParameter("make", make).setParameter("type", type).setParameter("price", price).setParameter("mileage", mileage)
                    .list());
        }
        if (mileage != 0 & price == 0 & !photo) {  // make+model+type - with mileage
            return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(basicSql + mmt + " and advert.mileage = :mileage")
                    .setParameter("model", model).setParameter("make", make).setParameter("type", type).setParameter("mileage", mileage)
                    .list());
        }
        if (mileage == 0 & price == 0 & !photo) {  // make+model+type -
            return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(basicSql + mmt)
                    .setParameter("model", model).setParameter("make", make).setParameter("type", type)
                    .list());
        }
        if (mileage == 0 & price != 0 & !photo) {  // make+model+type - with price
            return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(basicSql + mmt + " and advert.price = :price")
                    .setParameter("model", model).setParameter("make", make).setParameter("type", type).setParameter("price", price)
                    .list());
        }
        if (mileage != 0 & price == 0 & photo) {  // make+model+type - with mileage and photo
            return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(basicSql + mmt + " and advert.photoId not in (0) and advert.mileage = :mileage")
                    .setParameter("model", model).setParameter("make", make).setParameter("type", type).setParameter("mileage", mileage)
                    .list());
        }
        return null;
    }

    public List<Advert> findAllActive() {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(basicSql + "where advert.sold = false").list());
    }

    @Override
    public Advert findById(int id) {
        return (Advert) hibernateConnect.sessionMethodsWithReturn(
                session -> session.createQuery(basicSql + "where advert.id = :id")
                        .setParameter("id", id)
                        .uniqueResult());
    }

    @Override
    public List<Advert> findAll() {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(basicSql).list());
    }

    @Override
    public Advert save(Advert advert) {
        int id = (int) hibernateConnect.sessionMethodsWithReturn(session -> session.save(advert));
        advert.setId(id);
        return advert;
    }

    @Override
    public void update(Advert advert) {
        hibernateConnect.sessionVoidMethods(session -> session.update(advert));
    }

    @Override
    public void delete(int id) {
        hibernateConnect.sessionVoidMethods(session -> session.delete(Advert.builder().id(id).build()));
    }
}
