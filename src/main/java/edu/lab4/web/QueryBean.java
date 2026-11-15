package edu.lab4.web;

import edu.lab4.db.QueryDao;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class QueryBean implements Serializable {

    private static final String[] EXAMPLES = {
            "SELECT r.id, c.plate, c.brand, c.model, r.description, r.price, r.repair_date, r.status\n"
                    + "FROM repairs r JOIN cars c ON c.id = r.car_id\n"
                    + "WHERE r.status <> 'Виконано'\n"
                    + "ORDER BY r.repair_date",
            "SELECT c.plate, c.brand, c.model, COUNT(r.id) AS repairs_count, COALESCE(SUM(r.price), 0) AS total\n"
                    + "FROM cars c LEFT JOIN repairs r ON r.car_id = c.id\n"
                    + "GROUP BY c.id, c.plate, c.brand, c.model\n"
                    + "ORDER BY total DESC",
            "SELECT * FROM cars WHERE year < 2015 ORDER BY year"
    };

    private final QueryDao dao = new QueryDao();
    private String sql = EXAMPLES[0];
    private List<String> columns = new ArrayList<>();
    private List<List<String>> rows = new ArrayList<>();
    private boolean executed;

    public void run() {
        executed = false;
        columns = new ArrayList<>();
        rows = new ArrayList<>();
        try {
            QueryDao.Result result = dao.run(sql);
            columns = result.columns();
            rows = result.rows();
            executed = true;
        } catch (IllegalArgumentException e) {
            Messages.error(e.getMessage());
        } catch (SQLException e) {
            Messages.error("Помилка виконання запиту: " + e.getMessage());
        }
    }

    public void useExample(int number) {
        sql = EXAMPLES[number];
    }

    public String getSql() { return sql; }
    public void setSql(String sql) { this.sql = sql; }
    public List<String> getColumns() { return columns; }
    public List<List<String>> getRows() { return rows; }
    public boolean isExecuted() { return executed; }
}
