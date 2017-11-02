package securechat.server.data;

import org.springframework.data.repository.CrudRepository;
import securechat.server.model.Account;

import java.util.List;

/**
 * Created by sasch on 31/10/2017.
 */

public interface UserRepo extends CrudRepository<Account, Long> {

    List<Account> findByUsername(String Username);

}
