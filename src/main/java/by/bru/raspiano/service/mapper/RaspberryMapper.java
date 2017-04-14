package by.bru.raspiano.service.mapper;

import by.bru.raspiano.domain.*;
import by.bru.raspiano.service.dto.RaspberryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Raspberry and its DTO RaspberryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RaspberryMapper {

    RaspberryDTO raspberryToRaspberryDTO(Raspberry raspberry);

    List<RaspberryDTO> raspberriesToRaspberryDTOs(List<Raspberry> raspberries);

    Raspberry raspberryDTOToRaspberry(RaspberryDTO raspberryDTO);

    List<Raspberry> raspberryDTOsToRaspberries(List<RaspberryDTO> raspberryDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Raspberry raspberryFromId(Long id) {
        if (id == null) {
            return null;
        }
        Raspberry raspberry = new Raspberry();
        raspberry.setId(id);
        return raspberry;
    }
    

}
