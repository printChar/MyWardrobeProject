package model.dao;


import model.dto.Category;

import java.util.List;

public interface ICategoryDao {
    Category read(int id);
    List<Category> getAll();


}
