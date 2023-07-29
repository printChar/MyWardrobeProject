package service;


import model.dao.sql.StyleDaoSql;
import model.dto.Style;

import java.util.List;

public class StyleService {
    private final StyleDaoSql styleDaoSql = new StyleDaoSql();
    public List<Style> findAll() {
        return styleDaoSql.getAll();
    }
}
