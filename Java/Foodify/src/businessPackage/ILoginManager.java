package businessPackage;

import javafx.event.EventHandler;
import modelPackage.LoginEventArgs;

public interface ILoginManager {
    void Login(String user, String passwd, EventHandler<LoginEventArgs> handler);
}
