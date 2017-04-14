import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { RaspianoClimateMySuffixModule } from './climate/climate-my-suffix.module';
import { RaspianoI2cDeviceMySuffixModule } from './i-2-c-device/i-2-c-device-my-suffix.module';
import { RaspianoI2cSensorMySuffixModule } from './i-2-c-sensor/i-2-c-sensor-my-suffix.module';
import { RaspianoI2cControllerMySuffixModule } from './i-2-c-controller/i-2-c-controller-my-suffix.module';
import { RaspianoControllerActonMySuffixModule } from './controller-acton/controller-acton-my-suffix.module';
import { RaspianoRaspberryMySuffixModule } from './raspberry/raspberry-my-suffix.module';
import { RaspianoMeasurementMySuffixModule } from './measurement/measurement-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        RaspianoClimateMySuffixModule,
        RaspianoI2cDeviceMySuffixModule,
        RaspianoI2cSensorMySuffixModule,
        RaspianoI2cControllerMySuffixModule,
        RaspianoControllerActonMySuffixModule,
        RaspianoRaspberryMySuffixModule,
        RaspianoMeasurementMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoEntityModule {}
