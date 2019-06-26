package pl.konradgugala.ordertaxi.business.entities.repositories;

import pl.konradgugala.ordertaxi.business.entities.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {
  City findByTitle(String city_title);
}
