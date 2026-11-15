package edu.lab4.db;

import edu.lab4.model.Repair;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RepairDao {

    public List<Repair> findAll() throws SQLException {
        String sql = "SELECT r.id, r.car_id, c.plate, r.description, r.price, r.repair_date, r.status "
                + "FROM repairs r JOIN cars c ON c.id = r.car_id ORDER BY r.id";
        List<Repair> result = new ArrayList<>();
        try (Connection c = Db.connect();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Repair r = new Repair();
                r.setId(rs.getInt("id"));
                r.setCarId(rs.getInt("car_id"));
                r.setCarPlate(rs.getString("plate"));
                r.setDescription(rs.getString("description"));
                r.setPrice(rs.getBigDecimal("price"));
                r.setRepairDate(rs.getDate("repair_date").toLocalDate());
                r.setStatus(rs.getString("status"));
                result.add(r);
            }
        }
        return result;
    }

    public void insert(Repair r) throws SQLException {
        String sql = "INSERT INTO repairs (car_id, description, price, repair_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = Db.connect(); PreparedStatement ps = c.prepareStatement(sql)) {
            fill(ps, r);
            ps.executeUpdate();
        }
    }

    public void update(Repair r) throws SQLException {
        String sql = "UPDATE repairs SET car_id = ?, description = ?, price = ?, repair_date = ?, status = ? WHERE id = ?";
        try (Connection c = Db.connect(); PreparedStatement ps = c.prepareStatement(sql)) {
            fill(ps, r);
            ps.setInt(6, r.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = Db.connect(); PreparedStatement ps = c.prepareStatement("DELETE FROM repairs WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private static void fill(PreparedStatement ps, Repair r) throws SQLException {
        ps.setInt(1, r.getCarId());
        ps.setString(2, r.getDescription());
        ps.setBigDecimal(3, r.getPrice());
        ps.setDate(4, Date.valueOf(r.getRepairDate()));
        ps.setString(5, r.getStatus());
    }
}
