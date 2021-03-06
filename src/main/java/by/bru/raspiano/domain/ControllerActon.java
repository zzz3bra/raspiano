package by.bru.raspiano.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ControllerActon.
 */
@Entity
@Table(name = "controller_acton")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ControllerActon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "action_start", nullable = false)
    private ZonedDateTime actionStart;

    @NotNull
    @Column(name = "action_end", nullable = false)
    private ZonedDateTime actionEnd;

    @ManyToOne(optional = false)
    @NotNull
    private I2cController controller;

    @ManyToOne(optional = false)
    @NotNull
    private Climate climate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getActionStart() {
        return actionStart;
    }

    public ControllerActon actionStart(ZonedDateTime actionStart) {
        this.actionStart = actionStart;
        return this;
    }

    public void setActionStart(ZonedDateTime actionStart) {
        this.actionStart = actionStart;
    }

    public ZonedDateTime getActionEnd() {
        return actionEnd;
    }

    public ControllerActon actionEnd(ZonedDateTime actionEnd) {
        this.actionEnd = actionEnd;
        return this;
    }

    public void setActionEnd(ZonedDateTime actionEnd) {
        this.actionEnd = actionEnd;
    }

    public I2cController getController() {
        return controller;
    }

    public ControllerActon controller(I2cController i2cController) {
        this.controller = i2cController;
        return this;
    }

    public void setController(I2cController i2cController) {
        this.controller = i2cController;
    }

    public Climate getClimate() {
        return climate;
    }

    public ControllerActon climate(Climate climate) {
        this.climate = climate;
        return this;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ControllerActon controllerActon = (ControllerActon) o;
        if (controllerActon.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, controllerActon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ControllerActon{" +
            "id=" + id +
            ", actionStart='" + actionStart + "'" +
            ", actionEnd='" + actionEnd + "'" +
            '}';
    }
}
