package model.dao.sql;

import database.ConnectToDatabase;
import model.dao.IArticleDao;
import model.dto.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleDaoSql implements IArticleDao {

    private final Connection conn = ConnectToDatabase.createConnection();

    private final String SQL_CREATE_ARTICLE = "INSERT INTO ARTICLES(colourID, styleID, category, size, gender, modelID, brandID, picSrc, isClean) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQL_GET_ALL_ARTICLES = "SELECT * FROM ARTICLES";
    private final String SQL_GET_ARTICLES_BY_CATEGORY = "SELECT * FROM ARTICLES WHERE category =?";

    @Override
    public String create(Article article) {

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE)) {
            pstmt.setInt(1, article.getColour());
            pstmt.setInt(2, article.getStyle());
            pstmt.setString(3, article.getCategory().toString());
            pstmt.setString(4, article.getSize().toString());
            pstmt.setString(5, article.getGender().toString());
            pstmt.setInt(6, article.getModel());
            pstmt.setInt(7, article.getBrand());
            pstmt.setString(8, article.getPicSrc());
            pstmt.setBoolean(9, article.isClean());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ArticleDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "*** Article was successfully added to Wardrobedb Database. ***";
    }

    @Override
    public Article read(int id) {
        return null;
    }

    @Override
    public void update(Article article) {
    }

    @Override
    public void delete(Article article) {
    }

    @Override
    public List<Article> getArticlesBy(String value) {

        List<Article> allArticles = new ArrayList();

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ARTICLES_BY_CATEGORY)) {
            // pstmt.setEn(1, a.getCategory(category));
           pstmt.setString(1, value);
           // System.out.println(value);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Article a = new Article();
                    a.setColour(rs.getInt(2));
                    a.setStyle(rs.getInt(3));
                    a.setCategory(Category.valueOf(rs.getString(4).toUpperCase()));
                    a.setSize(Size.valueOf(rs.getString(5)));
                    a.setGender(Gender.valueOf(rs.getString(6).toUpperCase()));
                    a.setModel(rs.getInt(7));
                    a.setBrand(rs.getInt(8));
                    a.setPicSrc(rs.getString(9));
                    a.setClean(rs.getBoolean(10));
                    allArticles.add(a);
                }
            }
                } catch(SQLException ex){
                    Logger.getLogger(ArticleDaoSql.class.getName()).log(Level.SEVERE, null, ex);
                }
                return allArticles;

    }


            @Override
            public List<Article> getAll () {

                List<Article> allArticles = new ArrayList();

                try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL_ARTICLES);
                     ResultSet rs = pstmt.executeQuery()) {
                    if (!rs.wasNull()) {
                        while (rs.next()) {
                            Article article = new Article();
                            article.setId(rs.getInt(1));
                            article.setColour(rs.getInt(2));
                            article.setStyle(rs.getInt(3));
                            article.setCategory(Category.valueOf(rs.getString(4).toUpperCase()));
                            article.setSize(Size.valueOf(rs.getString(5)));
                            article.setGender(Gender.valueOf(rs.getString(6).toUpperCase()));
                            article.setModel(rs.getInt(7));
                            article.setBrand(rs.getInt(8));
                            article.setPicSrc(rs.getString(9));
                            article.setClean(rs.getBoolean(10));
                            allArticles.add(article);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ArticleDaoSql.class.getName()).log(Level.SEVERE, null, ex);
                }
                return allArticles;
            }
        }

