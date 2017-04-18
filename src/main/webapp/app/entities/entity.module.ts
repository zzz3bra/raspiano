import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { RaspianoClimateModule } from './climate/climate.module';
import { RaspianoI2cDeviceModule } from './i-2-c-device/i-2-c-device.module';
import { RaspianoI2cSensorModule } from './i-2-c-sensor/i-2-c-sensor.module';
import { RaspianoI2cControllerModule } from './i-2-c-controller/i-2-c-controller.module';
import { RaspianoControllerActonModule } from './controller-acton/controller-acton.module';
import { RaspianoRaspberryModule } from './raspberry/raspberry.module';
import { RaspianoMeasurementModule } from './measurement/measurement.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        RaspianoClimateModule,
        RaspianoI2cDeviceModule,
        RaspianoI2cSensorModule,
        RaspianoI2cControllerModule,
        RaspianoControllerActonModule,
        RaspianoRaspberryModule,
        RaspianoMeasurementModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoEntityModule {}
