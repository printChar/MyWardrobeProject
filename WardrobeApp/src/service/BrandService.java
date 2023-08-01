package service;

import model.dao.sql.BrandDaoSql;
import model.dto.Brand;

import java.util.List;

public class BrandService {
    private final BrandDaoSql brandDaoSql = new BrandDaoSql();
    public List<Brand> findAll() {
        return brandDaoSql.getAll();
    }
    public Brand getBy(int id){
        return brandDaoSql.read(id);
    }
}
