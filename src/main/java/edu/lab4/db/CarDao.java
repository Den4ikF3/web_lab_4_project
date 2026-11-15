package edu.lab4.db;

import edu.lab4.model.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarDao {

    public List<Car> findAll() throws SQLException {
        String sql = "SELECT id, plate, brand, model, year, owner_name FROM cars ORDER BY id";
        List<Car> result = new ArrayList<>();
        try (Connection c = Db.connect();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setPlate(rs.getString("plate"));
                car.setBrand(rs.getString("brand"));
                car.setModel(rs.getString("model"));
                car.setYear(rs.getInt("year"));
                car.setOwnerName(rs.getString("owner_name"));
                result.add(car);
            }
        }
        return result;
    }

    public void insert(Car car) throws SQLException {
        String sql = "INSERT INTO cars (plate, brand, model, year, owner_name) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = Db.connect(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, car.getPlate());
            ps.setString(2, car.getBrand());
            ps.setString(3, car.getModel());
            ps.setInt(4, car.getYear());
            ps.setString(5, car.getOwnerName());
            ps.executeUpdate();
        }
    }

    public void update(Car car) throws SQLException {
        String sql = "UPDATE cars SET plate = ?, brand = ?, model = ?, year = ?, owner_name = ? WHERE id = ?";
        try (Connection c = Db.connect(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, car.getPlate());
            ps.setString(2, car.getBrand());
            ps.setString(3, car.getModel());
            ps.setInt(4, car.getYear());
            ps.setString(5, car.getOwnerName());
            ps.setInt(6, car.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = Db.connect(); PreparedStatement ps = c.prepareStatement("DELETE FROM cars WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
