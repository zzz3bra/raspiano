package by.bru.raspiano.repository;

import by.bru.raspiano.domain.Climate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Climate entity.
 */
@SuppressWarnings("unused")
public interface ClimateRepository extends JpaRepository<Climate,Long> {

}
