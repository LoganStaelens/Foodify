package businessPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dataAccessPackage.IUserDBAccess;
import dataAccessPackage.UserDBAccess;
import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;
import modelPackage.Address;
import modelPackage.Gender;

public class UserManager implements IUserManager {

    private IUserDBAccess userDataAccess;

    public UserManager() {
        userDataAccess = new UserDBAccess();
    }
    
    public void createNewUser(String firstName, String lastName, Gender gender, String email, LocalDate birthDate, String phoneNumber, Address address, String password) throws DBConnectionException {

    }

    public List<String> getGenders() throws DBConnectionException {
        
        ResultSet result = userDataAccess.getGenders();

        List<String> data = new ArrayList<String>();

        try {
            while(result.next()) {
                String gender = result.getString("gender");
                data.add(gender);
            }

            result.close();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.RESULT_SET_EXCEPTION);
        }
        return data;
    }
}
