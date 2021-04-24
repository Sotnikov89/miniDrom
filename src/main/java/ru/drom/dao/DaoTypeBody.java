package ru.drom.dao;

import ru.drom.model.TypeBody;

import java.util.List;

public class DaoTypeBody implements Dao<TypeBody> {

    private final HibernateConnect hibernateConnect;

    public DaoTypeBody() {
        hibernateConnect = HibernateConnect.instOf();
    }

    @Override
    public TypeBody findById(int id) {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.get(TypeBody.class, id));
    }

    @Override
    public List<TypeBody> findAll() {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery("from TypeBody")).list();
    }

    @Override
    public TypeBody save(TypeBody typeBody) {
        return (TypeBody) hibernateConnect.sessionMethodsWithReturn(session -> session.save(typeBody));
    }

    @Override
    public void update(TypeBody typeBody) {
        hibernateConnect.sessionVoidMethods(session -> session.update(typeBody));
    }

    @Override
    public void delete(int id) {
        hibernateConnect.sessionVoidMethods(session -> session.delete(TypeBody.builder().id(id).build()));
    }
}
