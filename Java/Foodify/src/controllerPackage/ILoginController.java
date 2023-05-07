package controllerPackage;

import javafx.event.EventHandler;
import modelPackage.LoginEventArgs;

public interface ILoginController {
    void Login(String user, String passwd, EventHandler<LoginEventArgs> handler);
}
