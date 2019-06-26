package pl.konradgugala.ordertaxi.business.entities.repositories;

import pl.konradgugala.ordertaxi.business.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(String role);
}
