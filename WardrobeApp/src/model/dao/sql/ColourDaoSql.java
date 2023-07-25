package model.dao.sql;

import database.ConnectToDatabase;
import model.dao.IColourDao;
import model.dto.Colour;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
        return null;
    }
}

