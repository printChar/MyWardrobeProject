package service;

import model.dao.sql.ColourDaoSql;
import model.dto.Brand;
import model.dto.Colour;

import java.util.List;

public class ColourService {

    private final ColourDaoSql colourDaoSql = new ColourDaoSql();
    public List<Colour> findAll() {
        return colourDaoSql.getAll();
    }
    public Colour getBy(int id){
        return colourDaoSql.read(id);
    }
    public void addArticleColours(int articleId, int colourId){
        colourDaoSql.addColourToArticle(articleId, colourId);
    }
}
