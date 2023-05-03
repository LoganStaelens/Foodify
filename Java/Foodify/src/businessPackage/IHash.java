package businessPackage;

import exceptionPackage.HashException;

public interface IHash {
    String Hash(String text) throws HashException;
}
