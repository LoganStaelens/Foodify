package businessPackage;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import exceptionPackage.HashException;

public class SHA256Algorithm implements IHash {

    @Override
    public String Hash(String text) throws HashException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for(int i = 0; i < hashBytes.length; i++) {
                String hex = Integer.toHexString(0xff & hashBytes[i]);
                if(hex.length() == 1)
                    hexString.append(0);
                hexString.append(hex);
            }

            return hexString.toString();
        }
        catch(Exception e) {
            throw new HashException(e);
        }
    }
    
}
