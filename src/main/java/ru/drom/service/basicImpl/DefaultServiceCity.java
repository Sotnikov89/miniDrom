package ru.drom.service.basicImpl;

import ru.drom.dao.DaoCity;
import ru.drom.model.City;
import ru.drom.service.ServiceCity;

import java.util.List;

public class DefaultServiceCity implements ServiceCity {

    private final DaoCity daoCity;

    private DefaultServiceCity() { this.daoCity = DaoCity.instOf(); }

    public static DefaultServiceCity instOf() { return new DefaultServiceCity(); }

    @Override
    public City findById(int id) {
        return daoCity.findById(id);
    }

    @Override
    public List<City> findAll() {
        return daoCity.findAll();
    }

    @Override
    public City save(City city) {
        return daoCity.save(city);
    }

    @Override
    public void update(City city) { daoCity.update(city); }

    @Override
    public void delete(int id) {
        daoCity.delete(id);
    }
}
