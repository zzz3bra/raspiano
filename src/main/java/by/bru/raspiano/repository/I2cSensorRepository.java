package by.bru.raspiano.repository;

import by.bru.raspiano.domain.I2cSensor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the I2cSensor entity.
 */
@SuppressWarnings("unused")
public interface I2cSensorRepository extends JpaRepository<I2cSensor,Long> {

}
