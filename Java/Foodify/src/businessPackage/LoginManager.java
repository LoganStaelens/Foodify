package businessPackage;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import dataAccessPackage.IUserDataAccess;
import dataAccessPackage.UserDBAccess;
import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;
import exceptionPackage.HashException;
import exceptionPackage.StringTooLongException;
import modelPackage.Address;
import modelPackage.City;
import modelPackage.Country;
import modelPackage.Gender;
import modelPackage.User;

public class LoginManager implements ILoginManager {

    IUserDataAccess userDataAccess;
    IHash hashingAlgorithm;

    public LoginManager() {
        this.userDataAccess = new UserDBAccess();
        this.hashingAlgorithm = new SHA256Algorithm();
    }

    @Override
    public LoginResult Login(String emailInput, String passwdInput) throws HashException, DBConnectionException, StringTooLongException {
        ResultSet data;

        try {
            data = userDataAccess.FindUserByEmail(emailInput);

            if(data.next()) {
                String password = data.getString("User.password");
                
                String inputPasswordHash = this.hashingAlgorithm.Hash(passwdInput);
                if(inputPasswordHash.equals(password)) {
                    UUID uuid = UUID.fromString(data.getString("User.unique_id"));
                    Gender gender = Gender.X;
                    switch(data.getString("User.gender")) {
                        case "m":
                            gender = Gender.M;
                        case "f":
                            gender = Gender.F;
                        case "x":
                            gender = Gender.X;
                    }

                    boolean isAdmin = data.getBoolean("User.isAdmin");
                    String firstName = data.getString("User.firstName");
                    String lastName = data.getString("User.lastName");
                    String email = data.getString("User.email");
                    Date birthDatePrimal = data.getDate("User.birthDate");
                    String phoneNumber = data.getString("User.phoneNumber");
                    int addressID = data.getInt("Address.address_id");
                    String street = data.getString("Address.street");
                    int number = data.getInt("Address.number");
                    int cityID = data.getInt("City.city_id");
                    String cityName = data.getString("City.name");
                    String postCode = data.getString("City.postCode");
                    Country country = new Country(data.getString("City.country"));
                    City city = new City(cityID, cityName, postCode, country);
                    Address address = new Address(addressID, street, city);
                    User user = new User(uuid, gender, isAdmin, firstName, lastName, email, birthDatePrimal.toLocalDate(), phoneNumber, address);
                    data.close();
                    return new LoginResult(user, LoginStatus.SUCCESS);
                }
                else {
                    data.close();
                    return new LoginResult(null, LoginStatus.PASSWD_INCORRECT);
                }

                
            }
            else {
                return new LoginResult(null, LoginStatus.EMAIL_INCORRECT);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBConnectionException(DBConnectionExceptionTypes.PREPARED_STATEMENT_EXCEPTION);
        }
    }
}
