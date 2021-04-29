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
                session -> session.createQuery(basicSql + "where advert.created between :start and :end")
                        .setParameter("end", LocalDate.now().plusDays(1).atStartOfDay())
                        .setParameter("start", LocalDate.now().atStartOfDay())
                        .list());
    }

    public List<Advert> findAllByUserId(int userId) {
        return hibernateConnect.sessionMethodsWithReturn(
                session -> session.createQuery(basicSql + "where user.id = :id")
                        .setParameter("id", userId)
                        .list());
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
