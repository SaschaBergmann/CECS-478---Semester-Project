package securechat.server.controller;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import securechat.server.data.UserRepo;
import securechat.server.helper.Constants;
import securechat.server.model.Account;
import securechat.server.model.wrapper.Login1Response;
import securechat.server.model.wrapper.Login2RequestWrapper;
import securechat.server.model.wrapper.Login2Response;
import securechat.server.security.HashService;
import securechat.server.security.Random;
import securechat.server.security.integrity.HMACService;
import securechat.server.security.jwt.JWTService;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by sasch on 31/10/2017.
 */
@RestController
public class AccountController {

    private final JWTService jwtService;
    @Autowired
    private UserRepo userRepository;

    public AccountController(){
        jwtService = new JWTService();
    }

    @RequestMapping(value = "/account/register", method = RequestMethod.POST, produces = "application/json")
    Map RegisterRequest(@RequestBody Account account){
        if(userRepository.findByUsername(account.getUsername()).size() == 0){
            try {
                    account.setSalt(Random.createPseudoRandomKey(256));
                    account.setPwd(HashService.hash(account.getPwd(), account.getSalt()));
                    userRepository.save(account);
                    return Collections.singletonMap(Constants.RESPONSE_TAG, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Collections.singletonMap(Constants.RESPONSE_TAG, false);
    }

    @RequestMapping(value = "/account/login1", method = RequestMethod.POST, produces = "application/json")
    Login1Response LoginRequest(@RequestBody String username){

        Login1Response resp = new Login1Response();
        resp.setSuccessful(false);
        List<Account> accounts = userRepository.findByUsername(username);

        if (accounts.size() != 1) return resp;

        Account user = accounts.get(0);

        try {
            user.setLastChallenge(Random.createPseudoRandomKey(512));

            userRepository.save(user);

            resp.setSuccessful(true);
            resp.setChallenge(new String(user.getLastChallenge(), Constants.ENCODING_ISO));
            resp.setSalt(new String(user.getSalt(), Constants.ENCODING_ISO));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resp;

    }

    @RequestMapping(value = "/account/login2", method = RequestMethod.POST, produces = "application/json")
    Login2Response LoginRequest2(@RequestBody Login2RequestWrapper wrapper){

        Login2Response resp = new Login2Response();
        resp.setSuccessful(false);

        List<Account> accounts = userRepository.findByUsername(wrapper.getUsername());
        if (accounts.size() != 1) return resp;

        Account user = accounts.get(0);

        byte[] tag = wrapper.getTag();

        if(this.isTagCorrect(user, tag)){

            String jwt = jwtService.createJWT(user.getUsername(), Constants.JWT_ISSUER, Constants.JWT_SUBJECT, Constants.JWT_TIMEOUT);
            user.setToken(jwt);
            userRepository.save(user);

            resp.setSuccessful(true);
            resp.setJwt(jwt);
        }
        return resp;
    }

    private boolean isTagCorrect(Account user, byte[] tag) {

        byte[] serverTag = HMACService.CreateHMACHash(user.getLastChallenge(), user.getPwd());

        for (int i = 0; i < tag.length; i++) {
            if (tag[i] != serverTag[i]) return false;
        }
        return true;
    }
}
