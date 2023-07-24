package model.dao;

import model.dto.Size;

import java.util.List;

public interface ISizeDao {
    Size read(int id);
    List<Size> getAll(int id);
}
