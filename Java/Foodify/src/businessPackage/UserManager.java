package businessPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dataAccessPackage.AddressDBAccess;
import dataAccessPackage.CityDBAccess;
import dataAccessPackage.CountryDBAccess;
import dataAccessPackage.IAddressDBAccess;
import dataAccessPackage.ICityDBAccess;
import dataAccessPackage.ICountryDBAccess;
import dataAccessPackage.IUserDBAccess;
import dataAccessPackage.UserDBAccess;
import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;
import exceptionPackage.HashException;
import modelPackage.Address;
import modelPackage.Country;
import modelPackage.Gender;

public class UserManager implements IUserManager {

    private IUserDBAccess userDataAccess;
    private ICityDBAccess cityDBAccess;
    private IAddressDBAccess addressDBAccess;
    private ICountryDBAccess countryDBAccess;
    private IHash hashAlgorithm;

    public UserManager() {
        userDataAccess = new UserDBAccess();
        cityDBAccess = new CityDBAccess();
        addressDBAccess = new AddressDBAccess();
        countryDBAccess = new CountryDBAccess();
        hashAlgorithm = new SHA256Algorithm();
    }
    
    public void createNewUser(String firstName, String lastName, Gender gender, String email, LocalDate birthDate, String phoneNumber, Address address, String password) throws DBConnectionException {
        
        int cityID = cityDBAccess.checkCity(address.getCity().getCountry().GetCountryName(), address.getCity().getName(), address.getCity().getPostCode());
        int addressID;

        if (cityID == -1) {
            cityID = cityDBAccess.createNewCity(address.getCity().getCountry().GetCountryName(), address.getCity().getName(), address.getCity().getPostCode());
            addressID = addressDBAccess.createNewAddress(address.getStreet(), cityID, address.getNumber());
            
        }
        else {
            addressID = addressDBAccess.checkAddress(cityID, address.getStreet(), address.getNumber());

            if (addressID == -1) {
                addressID = addressDBAccess.createNewAddress(address.getStreet(), cityID, address.getNumber());
            }
        }

        userDataAccess.createNewUser(UUID.randomUUID().toString(), false, firstName, lastName, gender, email, birthDate, phoneNumber, addressID, password);
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

    @Override
    public List<Country> getCountries() throws DBConnectionException {
        ResultSet result = countryDBAccess.getCountries();

        List<Country> data = new ArrayList<Country>();

        try {
            while(result.next()) {
                String countryName = result.getString("name");
                data.add(new Country(countryName));
            }

            result.close();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.RESULT_SET_EXCEPTION);
        }
        return data;
    }

    @Override
    public String hashPassword(String passwd) throws HashException {
        return hashAlgorithm.hash(passwd);
    }

    @Override
    public boolean verifyPassword(String passwd1hash, String passwd2hash) {
        return passwd1hash.equals(passwd2hash);
    }
}
