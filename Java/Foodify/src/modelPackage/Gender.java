package modelPackage;

public enum Gender {
    M ("Masculin"),
    F ("Féminin"),
    X ("Autre");

    private String genderName;

    Gender(String genderName) {
        this.genderName = genderName;
    }

    public String getGenderName() {
        return this.genderName;
    }
}
