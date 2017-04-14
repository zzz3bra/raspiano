
const enum DeviceType {
    'SENSOR',
    'CONTROLLER'

};
export class I2cDeviceMySuffix {
    constructor(
        public id?: number,
        public deviceType?: DeviceType,
        public busAddress?: number,
        public name?: string,
        public deviceId?: number,
    ) {
    }
}
