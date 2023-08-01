package model.dao.sql;

import database.ConnectToDatabase;
import model.dao.IStyleDao;
import model.dto.Model;
import model.dto.Style;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StyleDaoSql implements IStyleDao {
    private final Connection conn = ConnectToDatabase.createConnection();
    private final String SQL_GET_ALL_STYLES = "SELECT * FROM STYLES";
    private final String SQL_GET_STYLES_BY_ID = "SELECT * FROM STYLES WHERE ID=?";
    @Override
    public Style read(int id) {

        Style style = new Style();

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_STYLES_BY_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    pstmt.setString(2, style.getName());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StyleDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return style;
    }
    @Override
    public List<Style> getAllBy(int id) {
        return null;
    }
    @Override
    public List<Style> getAll() {
        ArrayList<Style> allModels = new ArrayList();

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL_STYLES);
             ResultSet rs = pstmt.executeQuery()) {
            if (!rs.wasNull()) {
                while (rs.next()) {
                    Style style = new Style();
                    style.setId(rs.getInt(1));
                    style.setName(rs.getString(2));
                    allModels.add(style);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allModels;
    }
}
