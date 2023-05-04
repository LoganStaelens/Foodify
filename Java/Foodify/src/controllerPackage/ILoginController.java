package controllerPackage;

import javafx.event.EventHandler;

public interface ILoginController {
    void Login(String user, String passwd, EventHandler<LoginEventArgs> handler);
}
