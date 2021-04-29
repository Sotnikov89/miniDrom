package ru.drom.dao;

import ru.drom.model.User;

import java.util.List;

public class DaoUser implements Dao<User> {

    private final HibernateConnect hibernateConnect;

    private DaoUser() {
        hibernateConnect = HibernateConnect.instOf();
    }

    public static DaoUser instOf() { return new DaoUser(); }

    public User findByEmail(String email) {
        return (User) hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(
                "select user from User user join fetch user.city city where user.email = :email")
                .setParameter("email", email).uniqueResult());
    }

    @Override
    public User findById(int id) {
        return (User) hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(
                "select user from User user join fetch user.city city where user.id = :id")
                .setParameter("id", id).uniqueResult());
    }

    @Override
    public List<User> findAll() {
        return hibernateConnect.sessionMethodsWithReturn(session -> session.createQuery(
                "select user from User user join fetch user.city city").list());
    }

    @Override
    public User save(User user) {
        int id = (int) hibernateConnect.sessionMethodsWithReturn(session -> session.save(user));
        return findById(id);
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
