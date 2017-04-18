package by.bru.raspiano.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import by.bru.raspiano.domain.enumeration.DeviceType;

/**
 * A I2cDevice.
 */
@Entity
@Table(name = "i_2_c_device")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class I2cDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", nullable = false)
    private DeviceType deviceType;

    @Column(name = "bus_address")
    private Integer busAddress;

    @Column(name = "name")
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    private Raspberry device;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public I2cDevice deviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
        return this;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getBusAddress() {
        return busAddress;
    }

    public I2cDevice busAddress(Integer busAddress) {
        this.busAddress = busAddress;
        return this;
    }

    public void setBusAddress(Integer busAddress) {
        this.busAddress = busAddress;
    }

    public String getName() {
        return name;
    }

    public I2cDevice name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Raspberry getDevice() {
        return device;
    }

    public I2cDevice device(Raspberry raspberry) {
        this.device = raspberry;
        return this;
    }

    public void setDevice(Raspberry raspberry) {
        this.device = raspberry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        I2cDevice i2cDevice = (I2cDevice) o;
        if (i2cDevice.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, i2cDevice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "I2cDevice{" +
            "id=" + id +
            ", deviceType='" + deviceType + "'" +
            ", busAddress='" + busAddress + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
