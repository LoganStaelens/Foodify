package exceptionPackage;

public class DBConnectionException extends Exception{
    
    private DBConnectionExceptionTypes exceptionType;

    public DBConnectionException(DBConnectionExceptionTypes exceptionType) {
        this.exceptionType = exceptionType;
    }

    public DBConnectionExceptionTypes GetExceptionType() {
        return this.exceptionType;
    }

}
