package ru.drom.service.basicImpl;

import ru.drom.dao.DaoMake;
import ru.drom.model.Make;
import ru.drom.service.ServiceMake;

import java.util.List;

public class DefaultServiceMake implements ServiceMake {

    private final DaoMake daoMake;

    private DefaultServiceMake() { this.daoMake = DaoMake.instOf(); }

    public static DefaultServiceMake instOf() { return new DefaultServiceMake(); }

    @Override
    public Make findById(int id) {
        return daoMake.findById(id);
    }

    @Override
    public List<Make> findAll() {
        return daoMake.findAll();
    }

    @Override
    public Make save(Make make) {
        return daoMake.save(make);
    }

    @Override
    public void update(Make make) { daoMake.update(make); }

    @Override
    public void delete(int id) { daoMake.delete(id); }
}
