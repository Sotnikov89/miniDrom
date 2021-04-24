package ru.drom.dao;

import ru.drom.model.City;

import java.util.List;

public class DaoCity implements Dao<City>{

    private final HibernateConnect hibernateConnect;

    public DaoCity() {
        hibernateConnect = HibernateConnect.instOf();
    }

    @Override
    public City findById(int id) {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.get(City.class, id));
    }

    @Override
    public List<City> findAll() {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery("from City")).list();
    }

    @Override
    public City save(City city) {
        return (City) hibernateConnect.sessionMethodsWithReturn(session -> session.save(city));
    }

    @Override
    public void update(City city) {
        hibernateConnect.sessionVoidMethods(session -> session.update(city));
    }

    @Override
    public void delete(int id) {
        hibernateConnect.sessionVoidMethods(session -> session.delete(City.builder().id(id).build()));
    }
}
