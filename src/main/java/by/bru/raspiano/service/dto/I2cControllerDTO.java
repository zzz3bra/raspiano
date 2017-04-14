package by.bru.raspiano.service.dto;


import java.io.Serializable;
import java.util.Objects;
import by.bru.raspiano.domain.enumeration.ControllerType;

/**
 * A DTO for the I2cController entity.
 */
public class I2cControllerDTO implements Serializable {

    private Long id;

    private ControllerType controllerType;

    private Integer turnOffDelayMs;

    private Integer turnOnDelayMs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ControllerType getControllerType() {
        return controllerType;
    }

    public void setControllerType(ControllerType controllerType) {
        this.controllerType = controllerType;
    }
    public Integer getTurnOffDelayMs() {
        return turnOffDelayMs;
    }

    public void setTurnOffDelayMs(Integer turnOffDelayMs) {
        this.turnOffDelayMs = turnOffDelayMs;
    }
    public Integer getTurnOnDelayMs() {
        return turnOnDelayMs;
    }

    public void setTurnOnDelayMs(Integer turnOnDelayMs) {
        this.turnOnDelayMs = turnOnDelayMs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        I2cControllerDTO i2cControllerDTO = (I2cControllerDTO) o;

        if ( ! Objects.equals(id, i2cControllerDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "I2cControllerDTO{" +
            "id=" + id +
            ", controllerType='" + controllerType + "'" +
            ", turnOffDelayMs='" + turnOffDelayMs + "'" +
            ", turnOnDelayMs='" + turnOnDelayMs + "'" +
            '}';
    }
}
