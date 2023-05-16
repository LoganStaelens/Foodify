package businessPackage;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dataAccessPackage.AddressDBAccess;
import dataAccessPackage.CityDBAccess;
import dataAccessPackage.CountryDBAccess;
import dataAccessPackage.IAddressDataAccess;
import dataAccessPackage.ICityDataAccess;
import dataAccessPackage.ICountryDataAccess;
import dataAccessPackage.IUserDataAccess;
import dataAccessPackage.UserDBAccess;
import exceptionPackage.DataFetchException;
import exceptionPackage.DataFetchExceptionTypes;
import exceptionPackage.HashException;
import exceptionPackage.StringTooLongException;
import modelPackage.Address;
import modelPackage.City;
import modelPackage.Country;
import modelPackage.Gender;
import modelPackage.User;

public class UserManager implements IUserManager {

    private IUserDataAccess userDataAccess;
    private ICityDataAccess cityDBAccess;
    private IAddressDataAccess addressDBAccess;
    private ICountryDataAccess countryDBAccess;
    private IHash hashAlgorithm;

    public UserManager() {
        userDataAccess = new UserDBAccess();
        cityDBAccess = new CityDBAccess();
        addressDBAccess = new AddressDBAccess();
        countryDBAccess = new CountryDBAccess();
        hashAlgorithm = new SHA256Algorithm();
    }
    
    public void createNewUser(String firstName, String lastName, Gender gender, String email, LocalDate birthDate, String phoneNumber, Address address, String password) throws DataFetchException {
        
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

    public boolean findUserByEmail(String userEmail) throws DataFetchException {
        
        ResultSet result = userDataAccess.findUserByEmail(userEmail);

        try {
            if (result.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.RESULT_SET_EXCEPTION);
        }
        return false;
    }

    public List<String> getGenders() throws DataFetchException {
        
        ResultSet result = userDataAccess.getGenders();

        List<String> data = new ArrayList<String>();

        try {
            while(result.next()) {
                String gender = result.getString("gender");
                data.add(gender);
            }

            result.close();
        } catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.RESULT_SET_EXCEPTION);
        }
        return data;
    }

    @Override
    public List<Country> getCountries() throws DataFetchException {
        ResultSet result = countryDBAccess.getCountries();

        List<Country> data = new ArrayList<Country>();

        try {
            while(result.next()) {
                String countryName = result.getString("name");
                data.add(new Country(countryName));
            }

            result.close();
        } catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.RESULT_SET_EXCEPTION);
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

    @Override
    public List<User> findUsersByCountry(String countryIn) throws DataFetchException {
        ResultSet data;
        List<User> usersFound = new ArrayList<>();

        try {
            data = userDataAccess.getUsersByCountry(countryIn);

            while(data.next()) {
                UUID uuid = UUID.fromString(data.getString("User.unique_id"));
                Gender gender = Gender.X;
                switch(data.getString("User.gender")) {
                    case "m":
                        gender = Gender.M;
                    case "f":
                        gender = Gender.F;
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
                Address address = new Address(addressID, street, city, number);
                User user = new User(uuid, gender, isAdmin, firstName, lastName, email, birthDatePrimal.toLocalDate(), phoneNumber, address);
                usersFound.add(user);
            }

            return usersFound;
       
        } catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.PREPARED_STATEMENT_EXCEPTION);
        } catch (StringTooLongException e) {
            // Should never happen
            e.printStackTrace();
        }
        
        return usersFound;     
    }

    @Override
    public LoginResult login(String emailIn, String passwdIn)
            throws HashException, DataFetchException, StringTooLongException {
                ResultSet data;

                try {
                    data = userDataAccess.findUserByEmail(emailIn);
        
                    if(data.next()) {
                        String password = data.getString("User.password");
                        
                        String inputPasswordHash = this.hashAlgorithm.hash(passwdIn);
                        if(verifyPassword(inputPasswordHash, password)) {
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
                            Address address = new Address(addressID, street, city, number);
                            User user = new User(uuid, gender, isAdmin, firstName, lastName, email, birthDatePrimal.toLocalDate(), phoneNumber, address);
                            data.close();
                            
                            if (isAdmin) {
                                return new LoginResult(user, LoginStatus.SUCCESS_ADMIN);
                            }
        
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
                    throw new DataFetchException(DataFetchExceptionTypes.PREPARED_STATEMENT_EXCEPTION);
                }
    }
}
