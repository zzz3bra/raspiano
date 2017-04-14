package by.bru.raspiano.service.dto;


import java.io.Serializable;
import java.util.Objects;
import by.bru.raspiano.domain.enumeration.SensorType;

/**
 * A DTO for the I2cSensor entity.
 */
public class I2cSensorDTO implements Serializable {

    private Long id;

    private SensorType sensorType;

    private Integer minSensivity;

    private Integer maxSensivity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }
    public Integer getMinSensivity() {
        return minSensivity;
    }

    public void setMinSensivity(Integer minSensivity) {
        this.minSensivity = minSensivity;
    }
    public Integer getMaxSensivity() {
        return maxSensivity;
    }

    public void setMaxSensivity(Integer maxSensivity) {
        this.maxSensivity = maxSensivity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        I2cSensorDTO i2cSensorDTO = (I2cSensorDTO) o;

        if ( ! Objects.equals(id, i2cSensorDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "I2cSensorDTO{" +
            "id=" + id +
            ", sensorType='" + sensorType + "'" +
            ", minSensivity='" + minSensivity + "'" +
            ", maxSensivity='" + maxSensivity + "'" +
            '}';
    }
}
