package dataAccessPackage;

public class DBConnection {

    private static DBConnection INSTANCE;

    public static DBConnection getInstance() {
        if(INSTANCE == null)
            INSTANCE = new DBConnection();

        return INSTANCE;
    }

    private static final String USER = "root";
    private static final String PASSWD = "foodify";
    private static final String DB = "foodify";
    private static final String HOST = "127.0.0.1";

    private DBConnection() {
    }



}
