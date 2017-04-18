package by.bru.raspiano.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import by.bru.raspiano.domain.enumeration.DeviceType;

/**
 * A DTO for the I2cDevice entity.
 */
public class I2cDeviceDTO implements Serializable {

    private Long id;

    @NotNull
    private DeviceType deviceType;

    private Integer busAddress;

    private String name;

    private Long deviceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
    public Integer getBusAddress() {
        return busAddress;
    }

    public void setBusAddress(Integer busAddress) {
        this.busAddress = busAddress;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long raspberryId) {
        this.deviceId = raspberryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        I2cDeviceDTO i2cDeviceDTO = (I2cDeviceDTO) o;

        if ( ! Objects.equals(id, i2cDeviceDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "I2cDeviceDTO{" +
            "id=" + id +
            ", deviceType='" + deviceType + "'" +
            ", busAddress='" + busAddress + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
