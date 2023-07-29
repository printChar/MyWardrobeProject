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
    @Override
    public Colour read(int id) {
        return null;
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
}

