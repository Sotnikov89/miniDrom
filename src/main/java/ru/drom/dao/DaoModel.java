package ru.drom.dao;

import ru.drom.model.Model;

import java.util.List;

public class DaoModel implements Dao<Model> {

    private final HibernateConnect hibernateConnect;

    private DaoModel() { hibernateConnect = HibernateConnect.instOf(); }

    public static DaoModel instOf() { return new DaoModel(); }

    @Override
    public Model findById(int id) {
        return (Model) hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(
                "select model from Model model join fetch model.make make where model.id = :id")
                .setParameter("id", id).uniqueResult());
    }

    @Override
    public List<Model> findAll() {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(
                "select distinct model from Model model join fetch model.make make").list());
    }

    @Override
    public Model save(Model model) {
        int id = (int) hibernateConnect.sessionMethodsWithReturn(session -> session.save(model));
        return findById(id);
    }

    @Override
    public void update(Model model) {
        hibernateConnect.sessionVoidMethods(session -> session.update(model));
    }

    @Override
    public void delete(int id) {
        hibernateConnect.sessionVoidMethods(session -> session.delete(Model.builder().id(id).build()));
    }
}
