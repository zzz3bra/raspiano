package by.bru.raspiano.service.mapper;

import by.bru.raspiano.domain.*;
import by.bru.raspiano.service.dto.I2cControllerDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity I2cController and its DTO I2cControllerDTO.
 */
@Mapper(componentModel = "spring", uses = {I2cDeviceMapper.class, })
public interface I2cControllerMapper {

    @Mapping(source = "device.id", target = "deviceId")
    I2cControllerDTO i2cControllerToI2cControllerDTO(I2cController i2cController);

    List<I2cControllerDTO> i2cControllersToI2cControllerDTOs(List<I2cController> i2cControllers);

    @Mapping(source = "deviceId", target = "device")
    I2cController i2cControllerDTOToI2cController(I2cControllerDTO i2cControllerDTO);

    List<I2cController> i2cControllerDTOsToI2cControllers(List<I2cControllerDTO> i2cControllerDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default I2cController i2cControllerFromId(Long id) {
        if (id == null) {
            return null;
        }
        I2cController i2cController = new I2cController();
        i2cController.setId(id);
        return i2cController;
    }
    

}
