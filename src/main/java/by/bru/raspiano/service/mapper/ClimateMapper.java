package by.bru.raspiano.service.mapper;

import by.bru.raspiano.domain.*;
import by.bru.raspiano.service.dto.ClimateDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Climate and its DTO ClimateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClimateMapper {

    ClimateDTO climateToClimateDTO(Climate climate);

    List<ClimateDTO> climatesToClimateDTOs(List<Climate> climates);

    Climate climateDTOToClimate(ClimateDTO climateDTO);

    List<Climate> climateDTOsToClimates(List<ClimateDTO> climateDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Climate climateFromId(Long id) {
        if (id == null) {
            return null;
        }
        Climate climate = new Climate();
        climate.setId(id);
        return climate;
    }
    

}
