package model.dao.sql;

import database.ConnectToDatabase;
import model.dao.ISizeDao;
import model.dto.Size;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SizeDaoSql implements ISizeDao {

    private final Connection conn = ConnectToDatabase.createConnection();

    private final String SQL_GET_ALL_SIZES = "SELECT * FROM SIZES";

    @Override
    public Size read(int id) {
        return null;
    }

    @Override
    public List<Size> getAll(int id) {

        List<Size> allSizes = new ArrayList<>();
/*

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL_SIZES);
             ResultSet rs = pstmt.executeQuery()) {
            if (!rs.wasNull()) {
                while (rs.next()) {
                    Size size = new Size();
                    size.setValue(rs.getInt(1));
                    product.setProductOrderNumber(rs.getString(2));
                    product.setProductDescription(rs.getString(3));
                    product.setProductWidth(rs.getDouble(4));
                    product.setProductDepth(rs.getDouble(5));
                    product.setProductHeight(rs.getDouble(6));
                    product.setProductWeight(rs.getDouble(7));
                    product.setProductPrice(rs.getDouble(8));
                    allProducts.add(product);
                }
            }else {
                Product product = new Product();
                product.setProductId(129);
                product.setProductOrderNumber("");
                product.setProductDescription("something");
                product.setProductWidth(0.0);
                product.setProductDepth(0.0);
                product.setProductHeight(0.0);
                product.setProductWeight(0.0);
                product.setProductPrice(50.99);
                allProducts.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allProducts;
    }*/
        return null;
    }
}
