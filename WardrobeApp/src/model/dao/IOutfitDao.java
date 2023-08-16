package model.dao;

import model.dto.Article;
import model.dto.Outfit;

import java.util.List;

public interface IOutfitDao {
    String create(Outfit outfit);
    String delete(int id);
    List<Outfit> getOutfitsBy(String category);
    Outfit read(int id);
    List<Outfit> getAll();


}
