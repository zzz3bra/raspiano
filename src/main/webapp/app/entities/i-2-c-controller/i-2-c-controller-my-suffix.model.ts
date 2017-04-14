
const enum ControllerType {
    'MOTOR',
    'VALVE'

};
export class I2cControllerMySuffix {
    constructor(
        public id?: number,
        public controllerType?: ControllerType,
        public turnOffDelayMs?: number,
        public turnOnDelayMs?: number,
    ) {
    }
}
