package ru.drom.dao;

import ru.drom.model.User;

import java.util.List;

public class DaoUser implements Dao<User>{

    private final HibernateConnect hibernateConnect;

    public DaoUser() {
        hibernateConnect = HibernateConnect.instOf();
    }

    @Override
    public User findById(int id) {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.get(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery("select user from User user join fetch user.city city")).list();
    }

    @Override
    public User save(User user) {
        return (User) hibernateConnect.sessionMethodsWithReturn(session -> session.save(user));
    }

    @Override
    public void update(User user) {
        hibernateConnect.sessionVoidMethods(session -> session.update(user));
    }

    @Override
    public void delete(int id) {
        hibernateConnect.sessionVoidMethods(session -> session.delete(User.builder().id(id).build()));
    }
}
