package ru.drom.service.basicImpl;

import ru.drom.dao.DaoModel;
import ru.drom.model.Model;
import ru.drom.service.ServiceModel;

import java.util.List;

public class DefaultServiceModel implements ServiceModel {

    private final DaoModel daoModel;

    private DefaultServiceModel() { this.daoModel = DaoModel.instOf(); }

    public static DefaultServiceModel instOf() { return new DefaultServiceModel(); }

    @Override
    public Model findById(int id) {
        return daoModel.findById(id);
    }

    @Override
    public List<Model> findAll() {
        return daoModel.findAll();
    }

    @Override
    public Model save(Model model) {
        return daoModel.save(model);
    }

    @Override
    public void update(Model model) { daoModel.update(model); }

    @Override
    public void delete(int id) { daoModel.delete(id); }
}
