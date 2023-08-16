package model.dao.sql;

import database.ConnectToDatabase;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    private final String SQL_GET_ARTICLE_BY_ID = "SELECT * FROM ARTICLES WHERE ID =?";
    private final String SQL_UPDATE_ARTICLE_BY_ID = "UPDATE ARTICLES SET colourID=?, styleID=?, size=?, gender=?, modelID=?, brandID=?, isClean=?  WHERE ID=?";
    private static final String SQL_DELETE_ARTICLE = "DELETE FROM ARTICLES WHERE ID =?";

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

        Article a = new Article();

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ARTICLE_BY_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    a.setId(rs.getInt(1));
                    a.setColour(rs.getInt(2));
                    a.setStyle(rs.getInt(3));
                    a.setCategory(Category.valueOf(rs.getString(4).toUpperCase()));
                    a.setSize(Size.valueOf(rs.getString(5)));
                    a.setGender(Gender.valueOf(rs.getString(6).toUpperCase()));
                    a.setModel(rs.getInt(7));
                    a.setBrand(rs.getInt(8));
                    a.setPicSrc(rs.getString(9));
                    a.setClean(rs.getBoolean(10));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    @Override
    public String update(Article article) {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE_ARTICLE_BY_ID)) {
            //colourID=?, styleID=?, size=?, gender=?, modelID=?, brandID=?, isClean=?  WHERE ID=?"
            pstmt.setInt(1, article.getColour());
            pstmt.setInt(2, article.getStyle());
            pstmt.setString(3, article.getSize().toString());
            pstmt.setString(4, article.getGender().toString());
            pstmt.setInt(5, article.getModel());
            pstmt.setInt(6, article.getBrand());
            pstmt.setBoolean(7, article.isClean());
            pstmt.setInt(8, article.getId());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ArticleDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "*** Article was successfully updated to Wardrobedb Database. ***";
    }

    @Override
    public String delete(int id) {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE_ARTICLE)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            } catch(SQLException ex){
        Logger.getLogger(ArticleDaoSql.class.getName()).log(Level.SEVERE, null, ex);
    }
        return "*** Article was successfully deleted from Wardrobedb Database. ***";
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
                    a.setId(rs.getInt(1));
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
            if(allArticles.isEmpty()){
                System.out.println("*** No articles was found from Wardrobedb Database. ***");
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

