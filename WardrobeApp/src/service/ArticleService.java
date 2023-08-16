package service;

import model.dao.sql.ArticleDaoSql;
import model.dto.Article;

import java.util.List;

public class ArticleService {
    //Article communicator
    private final ArticleDaoSql articleDaoSql = new ArticleDaoSql();
    //creation req -> database
    public String create(Article article) {
        return articleDaoSql.create(article);
    }
    //return all obj req <- database
    public List<Article> findAll() {
        return articleDaoSql.getAll();
    }
    //updates req -> database
    public String update(Article article) {
        return articleDaoSql.update(article);
    }
    //delete req -> database
    public String delete(int id) {
        return articleDaoSql.delete(id);
    }
    //get obj by req <-> database
    public Article getBy(int id){
        return articleDaoSql.read(id);
    }
    public List<Article> findBy(String category){
        return articleDaoSql.getArticlesBy(category);
    }
}
