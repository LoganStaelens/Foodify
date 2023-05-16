package exceptionPackage;

public class DataFetchException extends Exception{
    
    private DataFetchExceptionTypes exceptionType;

    public DataFetchException(DataFetchExceptionTypes exceptionType) {
        this.exceptionType = exceptionType;
    }

    public DataFetchExceptionTypes GetExceptionType() {
        return this.exceptionType;
    }

}
