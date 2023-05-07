package modelPackage;

import javafx.event.ActionEvent;

public class LoginEventArgs extends ActionEvent {
    private String userUUID;
    private LoginStatus status;

    public LoginEventArgs(LoginStatus status) {
        this.status = status;
    }

    public LoginEventArgs(LoginStatus status, String userUUID) {
        this.status = status;
        this.userUUID = userUUID;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public LoginStatus getStatus() {
        return status;
    }
}
