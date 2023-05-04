package businessPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import controllerPackage.LoginEventArgs;
import controllerPackage.LoginStatus;
import dataAccessPackage.IUserDataAccess;
import dataAccessPackage.UserDBAccess;
import exceptionPackage.HashException;
import javafx.event.EventHandler;

public class LoginTask extends Task<LoginEventArgs> {

    private String user;
    private String passwd;

    IUserDataAccess userDataAccess;
    IHash hashingAlgorithm;

    public LoginTask(String user, String passwd, EventHandler<LoginEventArgs> onActionPerformed) {
        super(onActionPerformed);
        this.user = user;
        this.passwd = passwd;
        this.userDataAccess = new UserDBAccess();
        this.hashingAlgorithm = new SHA256Algorithm();
    }

    @Override
    public void run() {
        ResultSet data = userDataAccess.FindUserByEmail(user);

        try {
            if(data.next()) {
                String uuid = data.getString("unique_id");
                String email = data.getString("email");
                String passwd = data.getString("password");
                boolean isAdmin = data.getBoolean("isAdmin");

                
                String inputPasswordHash = this.hashingAlgorithm.Hash(this.passwd);
                if(inputPasswordHash.equals(passwd)) {
                    onActionPerformed.handle(new LoginEventArgs(LoginStatus.SUCCESS, uuid));
                }
                else {
                    onActionPerformed.handle(new LoginEventArgs(LoginStatus.PASSWD_INCORRECT));
                }

                data.close();
            }
            else {
                onActionPerformed.handle(new LoginEventArgs(LoginStatus.LOGIN_INCORRECT)); 
            }
        } catch (SQLException e) {
            onActionPerformed.handle(new LoginEventArgs(LoginStatus.ERROR)); 
            e.printStackTrace();
        }
        catch (HashException e) {
            onActionPerformed.handle(new LoginEventArgs(LoginStatus.ERROR)); 
            e.printStackTrace();
        }

        
    }
}
