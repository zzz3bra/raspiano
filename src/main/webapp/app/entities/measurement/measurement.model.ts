export class Measurement {
    constructor(
        public id?: number,
        public dateTime?: any,
        public value?: number,
        public sourceId?: number,
        public climateId?: number,
    ) {
    }
}
