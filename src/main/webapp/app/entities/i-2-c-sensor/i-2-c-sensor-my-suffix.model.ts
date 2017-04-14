
const enum SensorType {
    'TEMPERATURE',
    'HUMIDITY'

};
export class I2cSensorMySuffix {
    constructor(
        public id?: number,
        public sensorType?: SensorType,
        public minSensivity?: number,
        public maxSensivity?: number,
    ) {
    }
}
