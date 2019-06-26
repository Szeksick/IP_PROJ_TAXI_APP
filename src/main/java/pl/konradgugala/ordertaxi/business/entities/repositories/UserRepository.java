package pl.konradgugala.ordertaxi.business.entities.repositories;

import pl.konradgugala.ordertaxi.business.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    Long countByEmail(String email);
    Long countByUsername(String username);
}
