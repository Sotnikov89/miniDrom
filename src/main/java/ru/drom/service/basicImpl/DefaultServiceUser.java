package ru.drom.service.basicImpl;

import ru.drom.dao.DaoUser;
import ru.drom.model.User;
import ru.drom.service.ServiceUser;

import java.util.List;

public class DefaultServiceUser implements ServiceUser {

    private final DaoUser daoUser;

    private DefaultServiceUser() { this.daoUser = DaoUser.instOf(); }

    public static DefaultServiceUser instOf() { return new DefaultServiceUser(); }

    @Override
    public User findById(int id) {
        return daoUser.findById(id);
    }

    @Override
    public List<User> findAll() {
        return daoUser.findAll();
    }

    @Override
    public User save(User user) { return daoUser.save(user); }

    @Override
    public void update(User user) {
        daoUser.update(user);
    }

    @Override
    public void delete(int id) {
        daoUser.delete(id);
    }

    @Override
    public User findByEmail(String email) { return daoUser.findByEmail(email); }
}
