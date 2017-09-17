package securechat.server.controller;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sasch on 17/09/2017.
 */
@RestController
public class HelloController {

    @RequestMapping(method = RequestMethod.GET)
    public String helloWorld(){
        return "Hello World";
    }
}
