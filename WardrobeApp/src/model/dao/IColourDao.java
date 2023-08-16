package model.dao;

import model.dto.Colour;

import java.util.List;

public interface IColourDao {
    Colour read(int id);
    List<Colour> getAllBy(int id);
    List<Colour> getAll();
    void addColourToArticle(int id, int colourId);
    String add(Colour colour);

}
