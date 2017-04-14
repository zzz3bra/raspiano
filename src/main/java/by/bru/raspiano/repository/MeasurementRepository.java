package by.bru.raspiano.repository;

import by.bru.raspiano.domain.Measurement;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Measurement entity.
 */
@SuppressWarnings("unused")
public interface MeasurementRepository extends JpaRepository<Measurement,Long> {

}
