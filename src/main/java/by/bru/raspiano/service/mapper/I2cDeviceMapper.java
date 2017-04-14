package by.bru.raspiano.service.mapper;

import by.bru.raspiano.domain.*;
import by.bru.raspiano.service.dto.I2cDeviceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity I2cDevice and its DTO I2cDeviceDTO.
 */
@Mapper(componentModel = "spring", uses = {RaspberryMapper.class, })
public interface I2cDeviceMapper {

    @Mapping(source = "device.id", target = "deviceId")
    I2cDeviceDTO i2cDeviceToI2cDeviceDTO(I2cDevice i2cDevice);

    List<I2cDeviceDTO> i2cDevicesToI2cDeviceDTOs(List<I2cDevice> i2cDevices);

    @Mapping(source = "deviceId", target = "device")
    I2cDevice i2cDeviceDTOToI2cDevice(I2cDeviceDTO i2cDeviceDTO);

    List<I2cDevice> i2cDeviceDTOsToI2cDevices(List<I2cDeviceDTO> i2cDeviceDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default I2cDevice i2cDeviceFromId(Long id) {
        if (id == null) {
            return null;
        }
        I2cDevice i2cDevice = new I2cDevice();
        i2cDevice.setId(id);
        return i2cDevice;
    }
    

}
