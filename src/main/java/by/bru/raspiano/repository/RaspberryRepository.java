package by.bru.raspiano.repository;

import by.bru.raspiano.domain.Raspberry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Raspberry entity.
 */
@SuppressWarnings("unused")
public interface RaspberryRepository extends JpaRepository<Raspberry,Long> {

}
