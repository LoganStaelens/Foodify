package controllerPackage;

import businessPackage.IHash;
import businessPackage.SHA256Algorithm;
import exceptionPackage.HashException;

public class ApplicationController {
    
    private static ApplicationController instance;

    public static ApplicationController getInstance() {
        if(instance == null)
            instance = new ApplicationController();
        return instance;
    }

    private IHash hashingAlgorithm;

    private ApplicationController() {
        this.hashingAlgorithm = new SHA256Algorithm();
    }

    public String HashPassword(String pwd) throws HashException {
        return this.hashingAlgorithm.Hash(pwd);
    }

    
}
