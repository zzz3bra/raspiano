package by.bru.raspiano.repository;

import by.bru.raspiano.domain.I2cDevice;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the I2cDevice entity.
 */
@SuppressWarnings("unused")
public interface I2cDeviceRepository extends JpaRepository<I2cDevice,Long> {

}
