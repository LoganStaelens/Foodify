package controllerPackage;

import businessPackage.ApplicationManager;
import javafx.event.EventHandler;

public class LoginController implements ILoginController {

    public LoginController() {

    }

    @Override
    public void Login(String user, String passwd, EventHandler<LoginEventArgs> handler) {
        ApplicationManager.getInstance().Login(user, passwd, handler);
    }
    
}
