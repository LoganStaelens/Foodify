package modelPackage;

public class Menu {
    
    private int menuID;
    private String user;
    private int year;
    private int week;
    
    public Menu(int menuID, String user, int year, int week) {
        this.menuID = menuID;
        this.user = user;
        this.year = year;
        this.week = week;
    }

    public int getMenuID() {
        return menuID;
    }

    public String getUser() {
        return user;
    }

    public int getYear() {
        return year;
    }

    public int getWeek() {
        return week;
    }
}
