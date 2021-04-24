package ru.drom.dao;

import ru.drom.model.Make;

import java.util.List;

public class DaoMake implements Dao<Make>{

    private final HibernateConnect hibernateConnect;

    public DaoMake() {
        hibernateConnect = HibernateConnect.instOf();
    }

    @Override
    public Make findById(int id) {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.get(Make.class, id));
    }

    @Override
    public List<Make> findAll() {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery("select make from Make make join fetch make.models model")).list();
    }

    @Override
    public Make save(Make make) {
        return (Make) hibernateConnect.sessionMethodsWithReturn(session -> session.save(make));
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
