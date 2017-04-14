package by.bru.raspiano.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import by.bru.raspiano.domain.enumeration.ControllerType;

/**
 * A I2cController.
 */
@Entity
@Table(name = "i_2_c_controller")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class I2cController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "controller_type")
    private ControllerType controllerType;

    @Column(name = "turn_off_delay_ms")
    private Integer turnOffDelayMs;

    @Column(name = "turn_on_delay_ms")
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

    public I2cController controllerType(ControllerType controllerType) {
        this.controllerType = controllerType;
        return this;
    }

    public void setControllerType(ControllerType controllerType) {
        this.controllerType = controllerType;
    }

    public Integer getTurnOffDelayMs() {
        return turnOffDelayMs;
    }

    public I2cController turnOffDelayMs(Integer turnOffDelayMs) {
        this.turnOffDelayMs = turnOffDelayMs;
        return this;
    }

    public void setTurnOffDelayMs(Integer turnOffDelayMs) {
        this.turnOffDelayMs = turnOffDelayMs;
    }

    public Integer getTurnOnDelayMs() {
        return turnOnDelayMs;
    }

    public I2cController turnOnDelayMs(Integer turnOnDelayMs) {
        this.turnOnDelayMs = turnOnDelayMs;
        return this;
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
        I2cController i2cController = (I2cController) o;
        if (i2cController.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, i2cController.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "I2cController{" +
            "id=" + id +
            ", controllerType='" + controllerType + "'" +
            ", turnOffDelayMs='" + turnOffDelayMs + "'" +
            ", turnOnDelayMs='" + turnOnDelayMs + "'" +
            '}';
    }
}
