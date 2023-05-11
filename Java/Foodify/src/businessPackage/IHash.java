package businessPackage;

import exceptionPackage.HashException;

public interface IHash {
    String hash(String text) throws HashException;
}
