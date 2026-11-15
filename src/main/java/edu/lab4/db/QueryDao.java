package edu.lab4.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QueryDao {

    private static final int MAX_ROWS = 500;

    public record Result(List<String> columns, List<List<String>> rows) {
    }

    public Result run(String sql) throws SQLException {
        String query = sql == null ? "" : sql.trim();
        if (query.endsWith(";")) {
            query = query.substring(0, query.length() - 1).trim();
        }
        String lower = query.toLowerCase(Locale.ROOT);
        if (!(lower.startsWith("select") || lower.startsWith("with")) || query.contains(";")) {
            throw new IllegalArgumentException("Дозволено лише один запит SELECT");
        }

        try (Connection c = Db.connect()) {
            c.setAutoCommit(false);
            c.setReadOnly(true);
            try (Statement st = c.createStatement()) {
                st.setMaxRows(MAX_ROWS);
                try (ResultSet rs = st.executeQuery(query)) {
                    ResultSetMetaData md = rs.getMetaData();
                    int n = md.getColumnCount();
                    List<String> columns = new ArrayList<>();
                    for (int i = 1; i <= n; i++) {
                        columns.add(md.getColumnLabel(i));
                    }
                    List<List<String>> rows = new ArrayList<>();
                    while (rs.next()) {
                        List<String> row = new ArrayList<>();
                        for (int i = 1; i <= n; i++) {
                            String value = rs.getString(i);
                            row.add(value == null ? "NULL" : value);
                        }
                        rows.add(row);
                    }
                    return new Result(columns, rows);
                }
            } finally {
                c.rollback();
            }
        }
    }
}
