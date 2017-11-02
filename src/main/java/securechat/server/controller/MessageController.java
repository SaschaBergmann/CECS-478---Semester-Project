package securechat.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import securechat.server.model.Message;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by sasch on 27/09/2017.
 */
@RestController
public class MessageController {

    @RequestMapping(value = "/message/send", method = RequestMethod.POST, produces = "application/json")
    public Map sendMessage(){
        return Collections.singletonMap("response", true);
    }


    @RequestMapping(value = "/message/receive", method = RequestMethod.POST, produces = "application/json")
    public List<Message> receiveMessages(){
        List<Message> msgs = new LinkedList<>();


        return msgs;
    }

}
