package pl.konradgugala.ordertaxi.business.entities.repositories;

import pl.konradgugala.ordertaxi.business.entities.Drive;
import org.springframework.data.repository.CrudRepository;

public interface DriveRepository extends CrudRepository<Drive, Long> {
    Iterable<Drive> findAllByCity_Id(long city_id);
}
