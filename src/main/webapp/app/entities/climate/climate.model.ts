export class Climate {
    constructor(
        public id?: number,
        public minDesiredTemperature?: number,
        public maxDesiredTemperature?: number,
        public minDesiredHumidity?: number,
        public maxDesiredHumidity?: number,
    ) {
    }
}
