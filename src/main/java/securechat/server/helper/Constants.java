package securechat.server.helper;

/**
 * Created by sasch on 03/12/2017.
 */
public class Constants {
    public static String CONTENT_TYPE_HEADER_NAME = "Content-type";
    public static String CONTENT_TYPE_JSON = "application/json";
    public static String AUTHORIZATION_HEADER_TOKEN_NAME = "Authorization";
    public static String ENCODING_ISO = "ISO-8859-1";
    public static String RESPONSE_TAG = "response";

    public static String RSA_CIPHER_SUITE = "RSA/NONE/OAEPWithSHA256AndMGF1Padding";
    public static String SECURITY_PROVIDER = "BC";
    public static String RANDOM_NUMBER_GEN_SUITE = "SHA1PRNG";
    public static String HASH_VERSION = "SHA-256";


    public static String JWT_ISSUER = "SecureChatServer_NotFrench";
    public static String JWT_SUBJECT = "JWT Token";
    public static Integer JWT_TIMEOUT = 8000000;
}
