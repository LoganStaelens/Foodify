package businessPackage;

import java.util.List;
import java.util.Map;

import exceptionPackage.DBConnectionException;
import modelPackage.MenuView;
import modelPackage.User;

public interface IMenuManager {
    boolean createMenu(User user, Map<Integer, String> selectedTags) throws DBConnectionException;
    boolean hasAMenuAlready(User user) throws DBConnectionException;
    List<MenuView> getCurrentMenuFromUser(User user) throws DBConnectionException;
}
