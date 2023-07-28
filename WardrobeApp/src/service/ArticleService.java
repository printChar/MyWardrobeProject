package service;

import model.dao.sql.ArticleDaoSql;
import model.dto.Article;
import model.dto.Category;

import java.util.List;

public class ArticleService {
    //Article communicator
    private final ArticleDaoSql articleDaoSql = new ArticleDaoSql();
    //creation req -> database
    public void create(Article article) {
        articleDaoSql.create(article);
    }
    //return all obj req <- database
    public List<Article> findAll() {
        return articleDaoSql.getAll();
    }
    //updates req -> database
    public void update(Article article) {
        articleDaoSql.update(article);
    }
    //delete req -> database
    public void delete(Article article) {
        articleDaoSql.delete(article);
    }
    //get obj by req <-> database
    public Article getBy(int id){
        return articleDaoSql.read(id);
    }

    public List<Article> findBy(String category){
        return articleDaoSql.getArticlesBy(category);
    }
}
