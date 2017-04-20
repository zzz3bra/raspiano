package by.bru.raspiano.device.sensor;

import java.util.Date;
import java.util.Objects;

/**
 * A {@code Measurement} consists of
 * <ul>
 * <li>The {@link MeasureType}</li>
 * <li>The value</li>
 * <li>The creation {@link Date}</li>
 * </ul>
 *
 * @author Stefan Freitag
 */
public final class Measurement {
    private final Date createdAt;
    private final float value;
    private final MeasureType type;

    /**
     * Create a new {@code Measurement}.
     *
     * @param value measured value.
     * @param type  {@link MeasureType}.
     */
    private Measurement(final float value, final MeasureType type) {
        this(value, type, new Date());

    }

    private Measurement(final float value, final MeasureType type, final Date createdAt) {
        this.value = value;
        this.type = type;
        this.createdAt = createdAt;
    }


    /**
     * Create a new {@code Measurement}.
     *
     * @param value measured value.
     * @param type  {@link MeasureType}.
     * @return A new {@code Measurement}.
     */
    public static Measurement create(final float value, final MeasureType type) {
        Objects.requireNonNull(type, MeasureType.class.getCanonicalName() + " is null.");
        return new Measurement(value, type);
    }

    /**
     * Create a new {@code Measurement}.
     *
     * @param value      measured value.
     * @param type       {@link MeasureType}.
     * @param measuredAt Date when the measurement was taken.
     * @return A new {@code Measurement}.
     */
    public static Measurement create(final float value, final MeasureType type, final Date measuredAt) {
        return new Measurement(value, type, measuredAt);
    }

    /**
     * Return the measured value.
     *
     * @return measured value
     */
    public float getValue() {
        return this.value;
    }

    /**
     * Return the {@link MeasureType}.
     *
     * @return The {@link MeasureType}.
     */
    public MeasureType getType() {
        return this.type;
    }

    /**
     * Return the creation {@link Date}.
     *
     * @return The creation {@link Date}.
     */
    public Date getCreatedAt() {
        return new Date(this.createdAt.getTime());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Measurement that = (Measurement) o;

        if (Float.compare(that.value, value) != 0) {
            return false;
        }
        if (!createdAt.equals(that.createdAt)) {
            return false;
        }
        return type == that.type;

    }

    @Override
    public int hashCode() {
        int result = createdAt.hashCode();
        result = 31 * result + (value != +0.0f ? Float.floatToIntBits(value) : 0);
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Measurement{" +
            "createdAt=" + createdAt +
            ", value=" + value +
            ", type=" + type +
            '}';
    }

}
