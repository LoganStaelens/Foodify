package dataAccessPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection INSTANCE;

    public static DBConnection getInstance() throws SQLException {
        if(INSTANCE == null)
            INSTANCE = new DBConnection();

        return INSTANCE;
    }

    private static final String USER = "root";
    private static final String PASSWD = "foodify";
    private static final String DB = "foodify";
    private static final String HOST = "127.0.0.1";

    private Connection connection;

    private DBConnection() throws SQLException {

        this.connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/foodify?useSSL=false",
            USER,
            PASSWD
            );

    }

    public Connection getConnection() {
        return connection;
    }
}
