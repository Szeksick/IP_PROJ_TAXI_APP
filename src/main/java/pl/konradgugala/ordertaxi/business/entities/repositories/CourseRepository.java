package pl.konradgugala.ordertaxi.business.entities.repositories;

import pl.konradgugala.ordertaxi.business.entities.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
