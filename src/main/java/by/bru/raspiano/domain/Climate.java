package by.bru.raspiano.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Climate.
 */
@Entity
@Table(name = "climate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Climate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "min_desired_temperature")
    private Integer minDesiredTemperature;

    @Column(name = "max_desired_temperature")
    private Integer maxDesiredTemperature;

    @Column(name = "min_desired_humidity")
    private Integer minDesiredHumidity;

    @Column(name = "max_desired_humidity")
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

    public Climate minDesiredTemperature(Integer minDesiredTemperature) {
        this.minDesiredTemperature = minDesiredTemperature;
        return this;
    }

    public void setMinDesiredTemperature(Integer minDesiredTemperature) {
        this.minDesiredTemperature = minDesiredTemperature;
    }

    public Integer getMaxDesiredTemperature() {
        return maxDesiredTemperature;
    }

    public Climate maxDesiredTemperature(Integer maxDesiredTemperature) {
        this.maxDesiredTemperature = maxDesiredTemperature;
        return this;
    }

    public void setMaxDesiredTemperature(Integer maxDesiredTemperature) {
        this.maxDesiredTemperature = maxDesiredTemperature;
    }

    public Integer getMinDesiredHumidity() {
        return minDesiredHumidity;
    }

    public Climate minDesiredHumidity(Integer minDesiredHumidity) {
        this.minDesiredHumidity = minDesiredHumidity;
        return this;
    }

    public void setMinDesiredHumidity(Integer minDesiredHumidity) {
        this.minDesiredHumidity = minDesiredHumidity;
    }

    public Integer getMaxDesiredHumidity() {
        return maxDesiredHumidity;
    }

    public Climate maxDesiredHumidity(Integer maxDesiredHumidity) {
        this.maxDesiredHumidity = maxDesiredHumidity;
        return this;
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
        Climate climate = (Climate) o;
        if (climate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, climate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Climate{" +
            "id=" + id +
            ", minDesiredTemperature='" + minDesiredTemperature + "'" +
            ", maxDesiredTemperature='" + maxDesiredTemperature + "'" +
            ", minDesiredHumidity='" + minDesiredHumidity + "'" +
            ", maxDesiredHumidity='" + maxDesiredHumidity + "'" +
            '}';
    }
}
