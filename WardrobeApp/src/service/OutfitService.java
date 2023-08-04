package service;

import model.dao.sql.OutfitDaoSql;
import model.dto.Outfit;

import java.util.List;

public class OutfitService {
    private OutfitDaoSql outfitDaoSql = new OutfitDaoSql();
    public void create(Outfit outfit) {
        outfitDaoSql.create(outfit);
    }
    public List<Outfit> findAll() {
        return outfitDaoSql.getAll();
    }
    public List<Outfit> getOutfitsBy(String category){
        return outfitDaoSql.getOutfitsBy(category);
    }
}
