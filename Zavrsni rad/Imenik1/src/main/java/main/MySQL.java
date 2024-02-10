
package main;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Types.*;


public class MySQL {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    final String server;
    final String schema;
    final String user;
    final String pass;
    final String url;

    public MySQL(String server, String schema, String user, String pass) {
        this.server = server;
        this.schema = schema;
        this.user = user;
        this.pass = pass;
        url = String.format("jdbc:mysql://%s/%s", server, schema);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    private Class getClass(int type) {
        switch(type) {
            case CHAR:
            case VARCHAR:
            case LONGVARCHAR:
                return String.class;
            case INTEGER:
            case BIGINT:
                return Long.class;
            case TINYINT:
            case SMALLINT:
                return Integer.class;
            case BIT:
                return Boolean.class;
            case TIME:
                return LocalTime.class;
            case DATE:
                return LocalDate.class;
            case TIMESTAMP:
                return LocalDateTime.class;
            case FLOAT:
            case DECIMAL:
            case NUMERIC:
            case DOUBLE:
            case REAL:
                return Double.class;
            case BLOB:
            case BINARY:
            case VARBINARY:
            case LONGVARBINARY:
                return byte[].class;
        }
        return Object.class;
    }

    public List<Map<String,Object>> query(String sql, Object... params) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement s = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
            for (int i = 0; i < params.length; i++) {
                s.setObject(i + 1, params[i]);
            }
            ResultSet rs= s.executeQuery();
            ResultSetMetaData rsmd=rs.getMetaData();
            int n=rsmd.getColumnCount();
            String columns[]=new String[n];
            int types[]=new int[n];
            for(int i=0; i<n; i++) {
                columns[i] = rsmd.getColumnLabel(i + 1);
                types[i]=rsmd.getColumnType(i+1);
            }

            List<Map<String,Object>> table=new ArrayList<>();
            while(rs.next()) {
                Map<String,Object> row=new HashMap<>();
                for(int i=0; i<n; i++) {
                    row.put(columns[i],rs.getObject(i+1,getClass(types[i])));
                }
                table.add(row);
            }
            return table;
        }
    }

    public List<Integer> execute(String sql, Object... params) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement s = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                s.setObject(i + 1, params[i]);
            }
            s.execute();
            ResultSet r = s.getGeneratedKeys();
            List<Integer> keys = new ArrayList<>();
            while (r.next()) {
                keys.add(r.getInt(1));
            }
            r.close();
            s.close();
            return keys;
        }
    }
}
