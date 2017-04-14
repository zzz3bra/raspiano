package by.bru.raspiano.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Climate entity.
 */
public class ClimateDTO implements Serializable {

    private Long id;

    private Integer minDesiredTemperature;

    private Integer maxDesiredTemperature;

    private Integer minDesiredHumidity;

    private Integer maxDesiredHumidity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getMinDesiredTemperature() {
        return minDesiredTemperature;
    }

    public void setMinDesiredTemperature(Integer minDesiredTemperature) {
        this.minDesiredTemperature = minDesiredTemperature;
    }
    public Integer getMaxDesiredTemperature() {
        return maxDesiredTemperature;
    }

    public void setMaxDesiredTemperature(Integer maxDesiredTemperature) {
        this.maxDesiredTemperature = maxDesiredTemperature;
    }
    public Integer getMinDesiredHumidity() {
        return minDesiredHumidity;
    }

    public void setMinDesiredHumidity(Integer minDesiredHumidity) {
        this.minDesiredHumidity = minDesiredHumidity;
    }
    public Integer getMaxDesiredHumidity() {
        return maxDesiredHumidity;
    }

    public void setMaxDesiredHumidity(Integer maxDesiredHumidity) {
        this.maxDesiredHumidity = maxDesiredHumidity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClimateDTO climateDTO = (ClimateDTO) o;

        if ( ! Objects.equals(id, climateDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClimateDTO{" +
            "id=" + id +
            ", minDesiredTemperature='" + minDesiredTemperature + "'" +
            ", maxDesiredTemperature='" + maxDesiredTemperature + "'" +
            ", minDesiredHumidity='" + minDesiredHumidity + "'" +
            ", maxDesiredHumidity='" + maxDesiredHumidity + "'" +
            '}';
    }
}
