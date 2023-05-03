package modelPackage;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class User {
    private UUID uniqueID;
    private boolean isAdmin; 
    private Address address;
    private Gender gender;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private Optional<String> phoneNumber;
}
