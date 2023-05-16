package businessPackage;

import java.util.List;
import java.util.Map;

import exceptionPackage.DataFetchException;
import modelPackage.MenuView;
import modelPackage.User;

public interface IMenuManager {
    boolean createMenu(User user, Map<Integer, String> selectedTags) throws DataFetchException;
    boolean hasAMenuAlready(User user) throws DataFetchException;
    List<MenuView> getCurrentMenuFromUser(User user) throws DataFetchException;
}
