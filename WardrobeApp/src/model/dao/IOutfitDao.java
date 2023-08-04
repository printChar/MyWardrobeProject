package model.dao;

import model.dto.Article;
import model.dto.Outfit;

import java.util.List;

public interface IOutfitDao {
    String create(Outfit outfit);
    List<Outfit> getOutfitsBy(String category);
    List<Outfit> getAll();
    Outfit getOutfitBy(int id);
}
