package businessPackage;

import java.sql.ResultSet;
import java.sql.SQLException;

import dataAccessPackage.IUserDataAccess;
import dataAccessPackage.UserDBAccess;
import exceptionPackage.DBConnectionException;
import exceptionPackage.HashException;
import javafx.event.EventHandler;
import modelPackage.LoginEventArgs;
import modelPackage.LoginStatus;

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
        ResultSet data;

        try {
            data = userDataAccess.FindUserByEmail(user);

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
        } catch (HashException e) {
            //TODO: Remonter l'erreur -- temporaire
            onActionPerformed.handle(new LoginEventArgs(LoginStatus.ERROR)); 
            e.printStackTrace();
        } catch (SQLException | DBConnectionException e) {
            onActionPerformed.handle(new LoginEventArgs(LoginStatus.ERROR)); 
            e.printStackTrace();
        }
        
    }
}
