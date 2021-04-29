package ru.drom.service;

import ru.drom.dao.Dao;
import ru.drom.model.Advert;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ServiceAdvert extends Dao<Advert> {
    void saveAdvertByReq(HttpServletRequest req);

    List<Advert> findAllByUserId(int userId);

    List<Advert> findAllThisDay();

    List<Advert> findByFilter(HttpServletRequest req);
}
