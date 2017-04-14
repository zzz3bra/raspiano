package by.bru.raspiano.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Measurement.
 */
@Entity
@Table(name = "measurement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Measurement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;

    @Column(name = "jhi_value")
    private Integer value;

    @ManyToOne
    private I2cSensor source;

    @ManyToOne
    private Climate dataHistory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public Measurement dateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getValue() {
        return value;
    }

    public Measurement value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public I2cSensor getSource() {
        return source;
    }

    public Measurement source(I2cSensor i2cSensor) {
        this.source = i2cSensor;
        return this;
    }

    public void setSource(I2cSensor i2cSensor) {
        this.source = i2cSensor;
    }

    public Climate getDataHistory() {
        return dataHistory;
    }

    public Measurement dataHistory(Climate climate) {
        this.dataHistory = climate;
        return this;
    }

    public void setDataHistory(Climate climate) {
        this.dataHistory = climate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Measurement measurement = (Measurement) o;
        if (measurement.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, measurement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Measurement{" +
            "id=" + id +
            ", dateTime='" + dateTime + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
