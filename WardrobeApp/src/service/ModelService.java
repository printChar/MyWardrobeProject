package service;

import model.dao.sql.ModelDaoSql;
import model.dto.Model;

import java.util.List;

public class ModelService {
    private final ModelDaoSql modelDaoSql = new ModelDaoSql();
    public List<Model> findAll() {
        return modelDaoSql.getAll();
    }

}
