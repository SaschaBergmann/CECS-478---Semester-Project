package securechat.server.security;

import org.apache.commons.lang.ArrayUtils;
import securechat.server.helper.Constants;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

/**
 * Created by sasch on 01/11/2017.
 */
public class HashService {
    public static byte[] hash(byte[] input, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance(Constants.HASH_VERSION);
        byte[] concatinated = ArrayUtils.addAll(input,salt);
        md.update(concatinated); // Change this to "UTF-16" if needed
        byte[] digest = md.digest();
        return digest;
    }
}
