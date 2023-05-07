package controllerPackage;

import businessPackage.ApplicationManager;
import businessPackage.ILoginManager;
import javafx.event.EventHandler;
import modelPackage.LoginEventArgs;

public class LoginController implements ILoginController {

    private ILoginManager loginManager; 

    public LoginController() {
        loginManager = ApplicationManager.getInstance();
    }

    @Override
    public void Login(String user, String passwd, EventHandler<LoginEventArgs> handler) {
        loginManager.Login(user, passwd, handler);
    }
    
}
