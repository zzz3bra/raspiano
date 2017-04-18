package by.bru.raspiano.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Raspberry.
 */
@Entity
@Table(name = "raspberry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Raspberry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Climate climate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Raspberry name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Climate getClimate() {
        return climate;
    }

    public Raspberry climate(Climate climate) {
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
        Raspberry raspberry = (Raspberry) o;
        if (raspberry.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, raspberry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Raspberry{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
