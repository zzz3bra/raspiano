import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RaspianoSharedModule } from '../../shared';
import {
    I2cSensorService,
    I2cSensorPopupService,
    I2cSensorComponent,
    I2cSensorDetailComponent,
    I2cSensorDialogComponent,
    I2cSensorPopupComponent,
    I2cSensorDeletePopupComponent,
    I2cSensorDeleteDialogComponent,
    i2cSensorRoute,
    i2cSensorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...i2cSensorRoute,
    ...i2cSensorPopupRoute,
];

@NgModule({
    imports: [
        RaspianoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        I2cSensorComponent,
        I2cSensorDetailComponent,
        I2cSensorDialogComponent,
        I2cSensorDeleteDialogComponent,
        I2cSensorPopupComponent,
        I2cSensorDeletePopupComponent,
    ],
    entryComponents: [
        I2cSensorComponent,
        I2cSensorDialogComponent,
        I2cSensorPopupComponent,
        I2cSensorDeleteDialogComponent,
        I2cSensorDeletePopupComponent,
    ],
    providers: [
        I2cSensorService,
        I2cSensorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoI2cSensorModule {}
