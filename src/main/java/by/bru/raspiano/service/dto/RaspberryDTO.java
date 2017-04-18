package by.bru.raspiano.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Raspberry entity.
 */
public class RaspberryDTO implements Serializable {

    private Long id;

    private String name;

    private Long climateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        RaspberryDTO raspberryDTO = (RaspberryDTO) o;

        if ( ! Objects.equals(id, raspberryDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RaspberryDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
