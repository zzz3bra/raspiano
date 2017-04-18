package by.bru.raspiano.service.mapper;

import by.bru.raspiano.domain.*;
import by.bru.raspiano.service.dto.ControllerActonDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ControllerActon and its DTO ControllerActonDTO.
 */
@Mapper(componentModel = "spring", uses = {I2cControllerMapper.class, ClimateMapper.class, })
public interface ControllerActonMapper {

    @Mapping(source = "controller.id", target = "controllerId")
    @Mapping(source = "climate.id", target = "climateId")
    ControllerActonDTO controllerActonToControllerActonDTO(ControllerActon controllerActon);

    List<ControllerActonDTO> controllerActonsToControllerActonDTOs(List<ControllerActon> controllerActons);

    @Mapping(source = "controllerId", target = "controller")
    @Mapping(source = "climateId", target = "climate")
    ControllerActon controllerActonDTOToControllerActon(ControllerActonDTO controllerActonDTO);

    List<ControllerActon> controllerActonDTOsToControllerActons(List<ControllerActonDTO> controllerActonDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default ControllerActon controllerActonFromId(Long id) {
        if (id == null) {
            return null;
        }
        ControllerActon controllerActon = new ControllerActon();
        controllerActon.setId(id);
        return controllerActon;
    }
    

}
