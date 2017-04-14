package by.bru.raspiano.repository;

import by.bru.raspiano.domain.ControllerActon;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ControllerActon entity.
 */
@SuppressWarnings("unused")
public interface ControllerActonRepository extends JpaRepository<ControllerActon,Long> {

}
