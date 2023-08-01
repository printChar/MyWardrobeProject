package model.dao.sql;

import database.ConnectToDatabase;
import model.dao.IModelDao;
import model.dto.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModelDaoSql implements IModelDao {
    private final Connection conn = ConnectToDatabase.createConnection();
    private final String SQL_GET_ALL_MODELS = "SELECT * FROM MODELS";
    private final String SQL_GET_MODEL_BY_ID = "SELECT * FROM MODELS WHERE ID=?";
    @Override
    public Model read(int id) {

        Model model = new Model();

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_MODEL_BY_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    pstmt.setString(2, model.getValue());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }
    @Override
    public List<Model> getAll() {
        ArrayList<Model> allModels = new ArrayList();

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL_MODELS);
             ResultSet rs = pstmt.executeQuery()) {
            if (!rs.wasNull()) {
                while (rs.next()) {
                    Model model = new Model();
                    model.setId(rs.getInt(1));
                    model.setValue(rs.getString(2));
                    allModels.add(model);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allModels;
    }

}
