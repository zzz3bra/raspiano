package by.bru.raspiano.device.sensor.sht21;
/**
 * The Sensor resolution for temperature and relative humidity.
 * @author Stefan Freitag
 */
public enum Resolution {

    /**
     * Resolution:
     * <ul>
     * <li>Relative humidity: 12 bit </li>
     * <li>Temperature: 14 bit</li>
     * </ul>
     */
    RES_12_14BIT((byte) 0x00),
    /**
     * Resolution:
     * <ul>
     * <li>Relative humidity: 8 bit </li>
     * <li>Temperature: 12 bit</li>
     * </ul>
     */
    RES_8_12BIT((byte) 0x01),
    /**
     * Resolution:
     * <ul>
     * <li>Relative humidity: 10 bit </li>
     * <li>Temperature: 13 bit</li>
     * </ul>
     */
    RES_10_13BIT((byte) 0x80),
    /**
     * Resolution:
     * <ul>
     * <li>Relative humidity: 11 bit </li>
     * <li>Temperature: 11 bit</li>
     * </ul>
     */
    RES_11_11BIT((byte) 0x81);
    /**
     * The mask for accessing the resolution bits (bit 7 and 0) in user register.
     */
    private static final byte MASK = (byte) 0x81;

    /**
     * The byte value representing the resolution.
     */
    private final byte resolutionByte;

    /**
     * Create a new {@code Resolution}.
     *
     * @param resolutionByte The byte-encoded resolution.
     */
    Resolution(final byte resolutionByte) {
        this.resolutionByte = resolutionByte;
    }

    /**
     * Return the {@link Resolution} for a given byte value.
     *
     * @param resolution The byte value to analyze.
     * @return The matching {@link Resolution}.
     */
    public static Resolution getResolution(final byte resolution) {
        final byte maskedValue = (byte) (MASK & resolution);
        if (maskedValue == 0x00) {
            return RES_12_14BIT;
        } else if (maskedValue == 0x01) {
            return RES_8_12BIT;
        } else if (maskedValue == (byte) 0x80) {
            return RES_10_13BIT;
        } else {
            return RES_11_11BIT;
        }
    }

    /**
     * Return the byte-encoded resolution.
     *
     * @return byte-encoded resolution.
     */
    @SuppressWarnings("unused")
    public byte getResolutionByte() {
        return this.resolutionByte;
    }

    @Override
    public String toString() {
        return "Resolution{"
            + this.name() +
            '}';
    }
}
