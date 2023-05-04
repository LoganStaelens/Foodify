package dataAccessPackage;

import java.sql.ResultSet;

public interface IUserDataAccess {
    ResultSet FindUserByEmail(String userEmail);
}
