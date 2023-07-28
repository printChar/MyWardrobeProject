package model.dao;

import model.dto.Article;
import model.dto.Category;

import java.util.List;

//ArticleDao interface that provides simple CRUD operations for the Article domain
public interface IArticleDao {
    void create(Article article);
    Article read(int id);
    void update(Article article);
    void delete(Article article);
    List<Article>  getArticlesBy(String category);
    List<Article> getAll();


}
