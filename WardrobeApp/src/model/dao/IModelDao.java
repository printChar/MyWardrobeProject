package model.dao;

import model.dto.Model;

import java.util.List;

public interface IModelDao {
    Model read(int id);
    List<Model> getAll();
}
