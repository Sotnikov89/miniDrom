package ru.drom.dao;

import ru.drom.model.Make;

import java.util.List;

public class DaoMake implements Dao<Make> {

    private final HibernateConnect hibernateConnect;

    private DaoMake() { hibernateConnect = HibernateConnect.instOf(); }

    public static DaoMake instOf() { return new DaoMake(); }

    @Override
    public Make findById(int id) {
        return (Make) hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(
                "select make from Make make join fetch make.models models where make.id = :id")
                .setParameter("id", id).uniqueResult());
    }

    @Override
    public List<Make> findAll() {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(
                "select distinct make from Make make join fetch make.models models").list());
    }

    @Override
    public Make save(Make make) {
        int id = (int) hibernateConnect.sessionMethodsWithReturn(session -> session.save(make));
        return findById(id);
    }

    @Override
    public void update(Make make) {
        hibernateConnect.sessionVoidMethods(session -> session.update(make));
    }

    @Override
    public void delete(int id) {
        hibernateConnect.sessionVoidMethods(session -> session.delete(Make.builder().id(id).build()));
    }
}
