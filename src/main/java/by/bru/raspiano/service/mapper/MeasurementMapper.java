package by.bru.raspiano.service.mapper;

import by.bru.raspiano.domain.*;
import by.bru.raspiano.service.dto.MeasurementDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Measurement and its DTO MeasurementDTO.
 */
@Mapper(componentModel = "spring", uses = {I2cSensorMapper.class, ClimateMapper.class, })
public interface MeasurementMapper {

    @Mapping(source = "source.id", target = "sourceId")
    @Mapping(source = "dataHistory.id", target = "dataHistoryId")
    MeasurementDTO measurementToMeasurementDTO(Measurement measurement);

    List<MeasurementDTO> measurementsToMeasurementDTOs(List<Measurement> measurements);

    @Mapping(source = "sourceId", target = "source")
    @Mapping(source = "dataHistoryId", target = "dataHistory")
    Measurement measurementDTOToMeasurement(MeasurementDTO measurementDTO);

    List<Measurement> measurementDTOsToMeasurements(List<MeasurementDTO> measurementDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Measurement measurementFromId(Long id) {
        if (id == null) {
            return null;
        }
        Measurement measurement = new Measurement();
        measurement.setId(id);
        return measurement;
    }
    

}
