package model.dao;

import model.dto.Model;
import model.dto.Size;
import model.dto.Style;

import java.util.List;

public interface IStyleDao {

    Style read(int id);
    List<Style> getAllBy(int id);

    List<Style> getAll();

}
