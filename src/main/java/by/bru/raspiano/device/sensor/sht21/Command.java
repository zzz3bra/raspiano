package by.bru.raspiano.device.sensor.sht21;

/**
 * <p>Sensor commands, taken from data sheet, table 6.</p>
 * <p>There are two different operation modes to communicate
 * with the sensor: Hold Master mode or No Hold Master
 * mode. In the first case the SCL line is blocked (controlled
 * by sensor) during measurement process while in the latter
 * case the SCL line remains open for other communication
 * while the sensor is processing the measurement. No hold
 * master mode allows for processing other I2C
 * communication tasks on a bus while the sensor is
 * measuring.</p>
 * @author Stefan Freitag
 */
public enum Command {
    /**
     * Trigger temperature measurement hold master. Byte code 1110’0011.
     */
    @SuppressWarnings("unused")
    TRIG_T_MEASUREMENT_HM((byte) 0xe3),
    /**
     * Trigger humidity measurement hold master. Byte code 1110’0101.
     */
    @SuppressWarnings("unused")
    TRIG_RH_MEASUREMENT_HM((byte) 0xe5),
    /**
     * Trigger temperature measurement no hold master. Byte code 1111’0011.
     */
    TRIG_T_MEASUREMENT_POLL((byte) 0xf3),
    /**
     * Trigger humidity measurement no hold master. Byte code 1111’0101.
     */
    TRIG_RH_MEASUREMENT_POLL((byte) 0xf5),
    /**
     * Writing user register. Byte code 1110’0110.
     */
    @SuppressWarnings("unused")
    USER_REG_W((byte) 0xe6),
    /**
     * Reading user register. Byte code 1110’0111.
     */
    USER_REG_R((byte) 0xe7),
    /**
     * Soft reset. Byte code 1111’1110.
     * This command is used for rebooting the sensor system without switching the power off and on
     * again. Upon reception of this command, the sensor system reinitialises and starts operation according to the
     * default settings – with the exception of the heater bit in the user register. The soft reset takes less than
     * 15ms.
     */
    SOFT_RESET((byte) 0xfe);

    private final byte commandByte;

    /**
     * Create a new {@link Command}.
     * @param commandByte Command encoded as byte.
     */
    Command(final byte commandByte) {
        this.commandByte = commandByte;
    }


    /**
     * Return the command byte.
     *
     * @return The command byte.
     */
    public final byte getCommandByte() {
        return this.commandByte;
    }
}
