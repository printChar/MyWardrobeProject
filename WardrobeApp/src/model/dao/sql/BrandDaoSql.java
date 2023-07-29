package model.dao.sql;

import database.ConnectToDatabase;
import model.dao.IBrandDao;
import model.dto.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrandDaoSql implements IBrandDao {
    private final Connection conn = ConnectToDatabase.createConnection();
    private final String SQL_GET_ALL_BRANDS = "SELECT * FROM BRANDS";
    @Override
    public Brand read(int id) {
        return null;
    }

    @Override
    public List<Brand> getAll() {
        ArrayList<Brand> allBrands = new ArrayList();

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL_BRANDS);
             ResultSet rs = pstmt.executeQuery()) {
            if (!rs.wasNull()) {
                while (rs.next()) {
                    Brand brand = new Brand();
                    brand.setId(rs.getInt(1));
                    brand.setName(rs.getString(2));
                    allBrands.add(brand);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ColourDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allBrands;
    }
}
