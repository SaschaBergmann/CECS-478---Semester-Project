package securechat.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import securechat.server.data.MessageRepo;
import securechat.server.data.UserRepo;
import securechat.server.helper.Constants;
import securechat.server.model.Account;
import securechat.server.model.Message;

import java.util.*;

/**
 * Created by sasch on 27/09/2017.
 */
@RestController
public class MessageController {
    @Autowired
    private MessageRepo msgRepo;
    @Autowired
    private UserRepo userRepo;


    @RequestMapping(value = "/message/send", method = RequestMethod.POST, produces = "application/json")
    public Map sendMessage(@RequestBody Message msg){
        //TODO: Check token
        msgRepo.save(msg);
        return Collections.singletonMap(Constants.RESPONSE_TAG, true);
    }


    @RequestMapping(value = "/message/receive/{username}", method = RequestMethod.GET, produces = "application/json")
    public List<Message> receiveMessages(@PathVariable String username, @RequestHeader("Authorization") String token){

        List<Account> users = userRepo.findByUsername(username);
        if (users.size() != 1) return null;

        if (users.get(0).getToken().equals(token)){
            List<Message> msgs = msgRepo.findByRecipient(username);

            msgRepo.delete(msgs);
            return msgs;
        }

        return new ArrayList<Message>();
    }

}
