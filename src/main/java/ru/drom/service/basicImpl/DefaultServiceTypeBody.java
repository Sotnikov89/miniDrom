package ru.drom.service.basicImpl;

import ru.drom.dao.DaoTypeBody;
import ru.drom.model.TypeBody;
import ru.drom.service.ServiceTypeBody;

import java.util.List;

public class DefaultServiceTypeBody implements ServiceTypeBody {

    private final DaoTypeBody daoTypeBody;

    private DefaultServiceTypeBody() { this.daoTypeBody = DaoTypeBody.instOf(); }

    public static DefaultServiceTypeBody instOf() { return new DefaultServiceTypeBody(); }

    @Override
    public TypeBody findById(int id) {
        return daoTypeBody.findById(id);
    }

    @Override
    public List<TypeBody> findAll() {
        return daoTypeBody.findAll();
    }

    @Override
    public TypeBody save(TypeBody typeBody) {
        return daoTypeBody.save(typeBody);
    }

    @Override
    public void update(TypeBody typeBody) {
        daoTypeBody.update(typeBody);
    }

    @Override
    public void delete(int id) {
        daoTypeBody.delete(id);
    }
}
