package ru.drom.dao;

import ru.drom.model.Advert;

import java.time.LocalDate;
import java.util.List;

public class DaoAdvert implements Dao<Advert> {

    private final HibernateConnect hibernateConnect;

    public DaoAdvert() {
        hibernateConnect = HibernateConnect.instOf();
    }

    public List<Advert> findAllThisDay() {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery("select advert from Advert advert " +
                "join fetch advert.user user join fetch user.city city join fetch advert.model model " +
                "join fetch model.make make join fetch advert.typeBody typeBody " +
                "where advert.created between :start and :end")
                .setParameter("start", LocalDate.now().atStartOfDay())
                .setParameter("end", LocalDate.now().plusDays(1).atStartOfDay())
                .list();
    }

    public List<Advert> findAllWithPhoto() {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery("select advert from Advert advert " +
                "join fetch advert.user user join fetch user.city city join fetch advert.model model " +
                "join fetch model.make make join fetch advert.typeBody typeBody " +
                "where advert.photoId is not null")
                .list();
    }

    public List<Advert> findAllByMake(String make) {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery("select advert from Advert advert " +
                "join fetch advert.user user join fetch user.city city join fetch advert.model model " +
                "join fetch model.make make join fetch advert.typeBody typeBody " +
                "where make.name = :name")
                .setParameter("name", make)
                .list();
    }

    @Override
    public Advert findById(int id) {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery("select advert from Advert advert" +
                " join fetch advert.user user join fetch user.city city join fetch advert.model model" +
                " join fetch model.make make join fetch advert.typeBody typeBody where advert.id = :id", Advert.class))
                .setParameter("id", id).uniqueResult();
    }

    @Override
    public List<Advert> findAll() {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery("select advert from Advert advert" +
                " join fetch advert.user user join fetch user.city city join fetch advert.model model" +
                " join fetch model.make make join fetch advert.typeBody typeBody", Advert.class))
                .list();
    }

    @Override
    public Advert save(Advert advert) {
        return (Advert) hibernateConnect.sessionMethodsWithReturn(session -> session.save(advert));
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
