package securechat.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import securechat.server.data.UserRepo;
import securechat.server.model.Account;
import securechat.server.security.HashService;
import securechat.server.security.Random;
import securechat.server.security.integrity.HMACService;
import securechat.server.security.jwt.JWTService;

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
                    return Collections.singletonMap("response", true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Collections.singletonMap("response", false);
    }

    @RequestMapping(value = "/account/login1", method = RequestMethod.POST, produces = "application/json")
    Map LoginRequest(@RequestBody String username){

        List<Account> accounts = userRepository.findByUsername(username);

        if (accounts.size() != 1) return Collections.singletonMap("response", false);

        Account user = accounts.get(0);

        try {
            user.setLastChallenge(Random.createPseudoRandomKey(512));
            Map<Object, Object> m = new HashMap();

            m.put("response", true);
            m.put("challenge", user.getLastChallenge());
            m.put("salt", user.getSalt());

            return m;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Collections.singletonMap("response", false);

    }

    @RequestMapping(value = "/account/login2", method = RequestMethod.POST, produces = "application/json")
    Map LoginRequest2(@RequestBody Map<String, Object> map){

        if (!(map.containsKey("username")&&map.containsKey("tag")))return Collections.singletonMap("response", false);

        List<Account> accounts = userRepository.findByUsername(map.get("username").toString());
        if (accounts.size() != 1) return Collections.singletonMap("response", false);

        Account user = accounts.get(0);
        if(this.isTagCorrect(user, (byte[]) map.get("tag"))){

            String jwt = jwtService.createJWT(user.getUsername(), "SecureChatServer_NotFrench", "JWT Token", 8000000);

            Map<Object, Object> m = new HashMap();

            m.put("response", true);
            m.put("jwt", jwt);
            return m;
        }

        return Collections.singletonMap("response", false);
    }

    private boolean isTagCorrect(Account user, byte[] tag) {
        return tag.equals(HMACService.CreateHMACHash(user.getLastChallenge(), user.getPwd()));
    }
}
