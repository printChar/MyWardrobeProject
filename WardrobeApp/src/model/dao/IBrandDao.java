package model.dao;

import model.dto.Brand;
import model.dto.Category;

import java.util.List;

public interface IBrandDao {

    Brand read(int id);
    List<Brand> getAll();

}
