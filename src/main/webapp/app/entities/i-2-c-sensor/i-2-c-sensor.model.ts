
const enum SensorType {
    'TEMPERATURE',
    'HUMIDITY'

};
export class I2cSensor {
    constructor(
        public id?: number,
        public sensorType?: SensorType,
        public minSensivity?: number,
        public maxSensivity?: number,
        public deviceId?: number,
    ) {
    }
}
