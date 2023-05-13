package businessPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dataAccessPackage.AddressDBAccess;
import dataAccessPackage.CityDBAccess;
import dataAccessPackage.IAddressDBAccess;
import dataAccessPackage.ICityDBAccess;
import dataAccessPackage.IUserDBAccess;
import dataAccessPackage.UserDBAccess;
import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;
import modelPackage.Address;
import modelPackage.Gender;

public class UserManager implements IUserManager {

    private IUserDBAccess userDataAccess;
    private ICityDBAccess cityDBAccess;
    private IAddressDBAccess addressDBAccess;

    public UserManager() {
        userDataAccess = new UserDBAccess();
        cityDBAccess = new CityDBAccess();
        addressDBAccess = new AddressDBAccess();
    }
    
    public void createNewUser(String firstName, String lastName, Gender gender, String email, LocalDate birthDate, String phoneNumber, Address address, String password) throws DBConnectionException {
        
        int cityID = cityDBAccess.checkCity(address.getCity().getCountry().GetCountryName(), address.getCity().getName(), address.getCity().getPostCode());
        int addressID;

        if (cityID == -1) {
            cityID = cityDBAccess.createNewCity(address.getCity().getCountry().GetCountryName(), address.getCity().getName(), address.getCity().getPostCode());
            addressID = addressDBAccess.createNewAddress(address.getStreet(), cityID, address.getNumber());
            userDataAccess.createNewUser(UUID.randomUUID().toString(), false, firstName, lastName, gender, email, birthDate, phoneNumber, addressID, password);
        }
        else {
            addressID = addressDBAccess.checkAddress(cityID, address.getStreet(), address.getNumber());

            if (addressID == -1) {
                addressID = addressDBAccess.createNewAddress(address.getStreet(), cityID, address.getNumber());
                userDataAccess.createNewUser(UUID.randomUUID().toString(), false, firstName, lastName, gender, email, birthDate, phoneNumber, addressID, password);
            }
            else {
                userDataAccess.createNewUser(UUID.randomUUID().toString(), false, firstName, lastName, gender, email, birthDate, phoneNumber, addressID, password);
            }
        }
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
