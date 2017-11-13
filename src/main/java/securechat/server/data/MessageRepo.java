package securechat.server.data;

import org.springframework.data.repository.CrudRepository;
import securechat.server.model.Message;

import java.util.List;

/**
 * Created by sasch on 31/10/2017.
 */
public interface MessageRepo extends CrudRepository<Message, Long> {

    List<Message> findByRecipient(String recipient);

}