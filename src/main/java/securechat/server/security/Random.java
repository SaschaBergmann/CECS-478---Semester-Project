package securechat.server.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by sasch on 01/11/2017.
 */
public class Random {
    public static byte[] createPseudoRandomKey(int cipherBlockSize) throws NoSuchAlgorithmException {
        SecureRandom randomSecureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] rand = new byte[cipherBlockSize];
        randomSecureRandom.nextBytes(rand);

        return rand;
    }
}
