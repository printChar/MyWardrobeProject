package service;

import model.dao.sql.OutfitDaoSql;
import model.dto.Article;
import model.dto.Outfit;

import java.util.List;

public class OutfitService {
    private OutfitDaoSql outfitDaoSql = new OutfitDaoSql();
    public String create(Outfit outfit) {
        return outfitDaoSql.create(outfit);
    }

    public String delete(int id){
        return outfitDaoSql.delete(id);
    }
    public Outfit getBy(int id){
        return outfitDaoSql.read(id);
    }
    public List<Outfit> findAll() {
        return outfitDaoSql.getAll();
    }
    public List<Outfit> getOutfitsBy(String category){
        return outfitDaoSql.getOutfitsBy(category);
    }
}
