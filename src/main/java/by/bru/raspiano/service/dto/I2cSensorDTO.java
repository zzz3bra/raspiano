package by.bru.raspiano.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import by.bru.raspiano.domain.enumeration.SensorType;

/**
 * A DTO for the I2cSensor entity.
 */
public class I2cSensorDTO implements Serializable {

    private Long id;

    @NotNull
    private SensorType sensorType;

    private Integer minSensivity;

    private Integer maxSensivity;

    private Long deviceId;

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

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long i2cDeviceId) {
        this.deviceId = i2cDeviceId;
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
