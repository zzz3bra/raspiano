package by.bru.raspiano.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ControllerActon entity.
 */
public class ControllerActonDTO implements Serializable {

    private Long id;

    private ZonedDateTime actionStart;

    private ZonedDateTime actionEnd;

    private Long controllerId;

    private Long actionHistoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getActionStart() {
        return actionStart;
    }

    public void setActionStart(ZonedDateTime actionStart) {
        this.actionStart = actionStart;
    }
    public ZonedDateTime getActionEnd() {
        return actionEnd;
    }

    public void setActionEnd(ZonedDateTime actionEnd) {
        this.actionEnd = actionEnd;
    }

    public Long getControllerId() {
        return controllerId;
    }

    public void setControllerId(Long i2cControllerId) {
        this.controllerId = i2cControllerId;
    }

    public Long getActionHistoryId() {
        return actionHistoryId;
    }

    public void setActionHistoryId(Long climateId) {
        this.actionHistoryId = climateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ControllerActonDTO controllerActonDTO = (ControllerActonDTO) o;

        if ( ! Objects.equals(id, controllerActonDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ControllerActonDTO{" +
            "id=" + id +
            ", actionStart='" + actionStart + "'" +
            ", actionEnd='" + actionEnd + "'" +
            '}';
    }
}
