package by.bru.raspiano.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Measurement entity.
 */
public class MeasurementDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateTime;

    private Integer value;

    private Long sourceId;

    private Long climateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long i2cSensorId) {
        this.sourceId = i2cSensorId;
    }

    public Long getClimateId() {
        return climateId;
    }

    public void setClimateId(Long climateId) {
        this.climateId = climateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MeasurementDTO measurementDTO = (MeasurementDTO) o;

        if ( ! Objects.equals(id, measurementDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
            "id=" + id +
            ", dateTime='" + dateTime + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
