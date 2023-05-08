package businessPackage;

import exceptionPackage.DBConnectionException;
import exceptionPackage.HashException;
import exceptionPackage.StringTooLongException;

public interface ILoginManager {
    LoginResult Login(String user, String passwd) throws HashException, DBConnectionException, StringTooLongException;
}
