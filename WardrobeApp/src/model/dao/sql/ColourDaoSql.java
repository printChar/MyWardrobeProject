package model.dao.sql;

import database.ConnectToDatabase;
import model.dao.IColourDao;
import model.dto.Colour;
import model.dto.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ColourDaoSql implements IColourDao {
    private final Connection conn = ConnectToDatabase.createConnection();
    private final String SQL_GET_ALL_COLOURS = "SELECT * FROM COLOURS";
    private final String SQL_GET_COLOURS_BY_ID = "SELECT * FROM COLOURS WHERE ID=?";
    private final String SQL_UPDATE_ARTICLE_COLOURS = "UPDATE ARTICLE_COLOURS_TEST SET colourId=? WHERE articleId=?";

    @Override
    public Colour read(int id) {

        Colour colour = new Colour();

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_COLOURS_BY_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    pstmt.setString(2, colour.getValue());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ColourDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return colour;
    }
    @Override
    public List<Colour> getAllBy(int id) {
        ArrayList<Colour> allColours = new ArrayList();
        return null;
    }
    @Override
    public List<Colour> getAll() {
        ArrayList<Colour> allColours = new ArrayList();

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL_COLOURS);
             ResultSet rs = pstmt.executeQuery()) {
            if (!rs.wasNull()) {
                while (rs.next()) {
                    Colour colour = new Colour();
                    colour.setId(rs.getInt(1));
                    colour.setValue(rs.getString(2));
                    allColours.add(colour);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ColourDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allColours;
    }
    @Override
    public void addColourToArticle(int articleId, int colourId) {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE_ARTICLE_COLOURS)) {
            pstmt.setInt(1, articleId);
            pstmt.setInt(2, colourId);
            pstmt.executeUpdate();

        }catch (SQLException ex){
            Logger.getLogger(ColourDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String add(Colour colour) {
        return null;
    }
}

