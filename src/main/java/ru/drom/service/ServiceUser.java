package ru.drom.service;

import ru.drom.dao.Dao;
import ru.drom.model.User;

public interface ServiceUser extends Dao<User> {
    User findByEmail(String email);
}
