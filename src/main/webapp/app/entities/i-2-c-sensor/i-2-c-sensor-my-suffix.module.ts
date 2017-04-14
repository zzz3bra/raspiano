import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RaspianoSharedModule } from '../../shared';
import {
    I2cSensorMySuffixService,
    I2cSensorMySuffixPopupService,
    I2cSensorMySuffixComponent,
    I2cSensorMySuffixDetailComponent,
    I2cSensorMySuffixDialogComponent,
    I2cSensorMySuffixPopupComponent,
    I2cSensorMySuffixDeletePopupComponent,
    I2cSensorMySuffixDeleteDialogComponent,
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
        I2cSensorMySuffixComponent,
        I2cSensorMySuffixDetailComponent,
        I2cSensorMySuffixDialogComponent,
        I2cSensorMySuffixDeleteDialogComponent,
        I2cSensorMySuffixPopupComponent,
        I2cSensorMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        I2cSensorMySuffixComponent,
        I2cSensorMySuffixDialogComponent,
        I2cSensorMySuffixPopupComponent,
        I2cSensorMySuffixDeleteDialogComponent,
        I2cSensorMySuffixDeletePopupComponent,
    ],
    providers: [
        I2cSensorMySuffixService,
        I2cSensorMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoI2cSensorMySuffixModule {}
