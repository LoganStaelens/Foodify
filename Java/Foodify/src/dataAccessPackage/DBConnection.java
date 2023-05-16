package dataAccessPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import exceptionPackage.DataFetchException;
import exceptionPackage.DataFetchExceptionTypes;

public class DBConnection {

    private static DBConnection INSTANCE;

    public static DBConnection getInstance() throws DataFetchException {
        if(INSTANCE == null)
            INSTANCE = new DBConnection();

        return INSTANCE;
    }

    private static final String USER = "root";
    private static final String PASSWD = "foodify";
    private static final String DB = "foodify";
    private static final String HOST = "127.0.0.1";

    private Connection connection;

    private DBConnection() throws DataFetchException {

        try {
            this.connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/foodify?useSSL=false",
                USER,
                PASSWD
                );
        } 
        catch (SQLTimeoutException e) {
            throw new DataFetchException(DataFetchExceptionTypes.TIMEOUT_EXCEPTION);
        } 
        catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.CONNECTION_EXCEPTION);
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws DataFetchException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.CLOSE_EXCEPTION);
        }
    }
}
