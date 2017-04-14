package by.bru.raspiano.repository;

import by.bru.raspiano.domain.I2cController;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the I2cController entity.
 */
@SuppressWarnings("unused")
public interface I2cControllerRepository extends JpaRepository<I2cController,Long> {

}
