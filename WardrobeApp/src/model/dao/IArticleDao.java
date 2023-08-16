package model.dao;

import model.dto.Article;
import model.dto.Category;

import java.util.List;

//ArticleDao interface that provides simple CRUD operations for the Article domain
public interface IArticleDao {
    String create(Article article);
    Article read(int id);
    String update(Article article);
    String delete(int id);
    List<Article>  getArticlesBy(String category);
    List<Article> getAll();


}
