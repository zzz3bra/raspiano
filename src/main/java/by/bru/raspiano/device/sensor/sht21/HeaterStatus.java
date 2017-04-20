package by.bru.raspiano.device.sensor.sht21;

/**
 * The heater is intended to be used for functionality diagnosis – relative humidity drops upon rising
 * temperature. The heater consumes about 5.5mW and provides a temperature increase of about 0.5 – 1.5°C.
 * @author Stefan Freitag
 */
public enum HeaterStatus {
    /**
     * Enabled heater.
     */
    HEATER_ON((byte) 0x04),
    /**
     * Disabled heater.
     */
    HEATER_OFF((byte) 0x00);
    /**
     * Mask for accessing the heater bit (bit 2) in the user register.
     */
    private static final byte MASK = 0x04;
    private final byte heaterByte;

    /**
     * Create a new {@code HeaterStatus}.
     *
     * @param heaterByte The byte-encoded heater status
     */
    HeaterStatus(final byte heaterByte) {
        this.heaterByte = heaterByte;
    }

    public static HeaterStatus getStatus(final byte heaterByte) {
        if ((heaterByte & MASK) == HEATER_OFF.getByte()) {
            return HEATER_OFF;
        }
        return HEATER_ON;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public byte getByte() {
        return this.heaterByte;
    }

}
