package by.bru.raspiano.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import by.bru.raspiano.domain.enumeration.SensorType;

/**
 * A I2cSensor.
 */
@Entity
@Table(name = "i_2_c_sensor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class I2cSensor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "sensor_type")
    private SensorType sensorType;

    @Column(name = "min_sensivity")
    private Integer minSensivity;

    @Column(name = "max_sensivity")
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

    public I2cSensor sensorType(SensorType sensorType) {
        this.sensorType = sensorType;
        return this;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public Integer getMinSensivity() {
        return minSensivity;
    }

    public I2cSensor minSensivity(Integer minSensivity) {
        this.minSensivity = minSensivity;
        return this;
    }

    public void setMinSensivity(Integer minSensivity) {
        this.minSensivity = minSensivity;
    }

    public Integer getMaxSensivity() {
        return maxSensivity;
    }

    public I2cSensor maxSensivity(Integer maxSensivity) {
        this.maxSensivity = maxSensivity;
        return this;
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
        I2cSensor i2cSensor = (I2cSensor) o;
        if (i2cSensor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, i2cSensor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "I2cSensor{" +
            "id=" + id +
            ", sensorType='" + sensorType + "'" +
            ", minSensivity='" + minSensivity + "'" +
            ", maxSensivity='" + maxSensivity + "'" +
            '}';
    }
}
