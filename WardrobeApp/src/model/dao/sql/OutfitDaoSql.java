package model.dao.sql;

import database.ConnectToDatabase;
import model.dao.IOutfitDao;
import model.dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OutfitDaoSql implements IOutfitDao {
    private final Connection conn = ConnectToDatabase.createConnection();
    private final String SQL_CREATE_OUTFIT = "INSERT INTO OUTFITS(topArticleID, bottomArticleID, shoeArticleID, styleID) VALUES (?, ?, ?, ?)";
    private final String SQL_GET_OUTFIT_BY_ID = "SELECT * FROM OUTFITS WHERE ID=?";
    private final String SQL_GET_ALL_OUTFITS = "SELECT * FROM OUTFITS";


    @Override
    public String create(Outfit outfit) {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_CREATE_OUTFIT)) {
            pstmt.setInt(1, outfit.getTop());
            pstmt.setInt(2, outfit.getBottom());
            pstmt.setInt(3, outfit.getShoes());
            pstmt.setInt(4, outfit.getStyle());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ArticleDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "*** Outfit was successfully added to Wardrobedb Database. ***";
    }
    @Override
    public List<Outfit> getOutfitsBy(String category) {
        return null;
    }
    @Override
    public List<Outfit> getAll() {
        List<Outfit> allOutfits = new ArrayList();

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL_OUTFITS);
             ResultSet rs = pstmt.executeQuery()) {
            if (!rs.wasNull()) {
                while (rs.next()) {
                    Outfit outfit = new Outfit();
                    outfit.setId(rs.getInt(1));
                    outfit.setTop(rs.getInt(2));
                    outfit.setBottom(rs.getInt(3));
                    outfit.setShoes(rs.getInt(4));
                    outfit.setStyle(rs.getInt(5));
                    allOutfits.add(outfit);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allOutfits;

    }

    @Override
    public Outfit getOutfitBy(int id) {
        Outfit outfit = new Outfit();

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL_OUTFITS)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    outfit.setId(rs.getInt(1));
                    outfit.setTop(rs.getInt(2));
                    outfit.setBottom(rs.getInt(3));
                    outfit.setShoes(rs.getInt(4));
                    outfit.setStyle(rs.getInt(5));
                }
            }
        } catch(SQLException ex){
            Logger.getLogger(ArticleDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outfit;
    }
}
