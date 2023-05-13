package businessPackage;

import modelPackage.User;

public class LoginResult {
    private User user;
    private LoginStatus status;

    
    public LoginResult(User user, LoginStatus status) {
        this.user = user;
        this.status = status;
    }


    public User getUser() {
        return user;
    }


    public LoginStatus getStatus() {
        return status;
    }

    
    
}
