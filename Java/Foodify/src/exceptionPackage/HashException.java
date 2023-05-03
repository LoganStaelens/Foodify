package exceptionPackage;

public class HashException extends Exception {
    Exception cause;

    public HashException(Exception cause) {
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return cause.getMessage();
    }

    public Exception getBaseException() {
        return cause;
    }
}
