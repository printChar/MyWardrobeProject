package service;

import model.dao.sql.ColourDaoSql;
import model.dto.Colour;

import java.util.List;

public class ColourService {

    private final ColourDaoSql colourDaoSql = new ColourDaoSql();
    public List<Colour> findAll() {
        return colourDaoSql.getAll();
    }
}
