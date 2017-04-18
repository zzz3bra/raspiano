import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RaspianoSharedModule } from '../../shared';
import {
    I2cDeviceService,
    I2cDevicePopupService,
    I2cDeviceComponent,
    I2cDeviceDetailComponent,
    I2cDeviceDialogComponent,
    I2cDevicePopupComponent,
    I2cDeviceDeletePopupComponent,
    I2cDeviceDeleteDialogComponent,
    i2cDeviceRoute,
    i2cDevicePopupRoute,
} from './';

const ENTITY_STATES = [
    ...i2cDeviceRoute,
    ...i2cDevicePopupRoute,
];

@NgModule({
    imports: [
        RaspianoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        I2cDeviceComponent,
        I2cDeviceDetailComponent,
        I2cDeviceDialogComponent,
        I2cDeviceDeleteDialogComponent,
        I2cDevicePopupComponent,
        I2cDeviceDeletePopupComponent,
    ],
    entryComponents: [
        I2cDeviceComponent,
        I2cDeviceDialogComponent,
        I2cDevicePopupComponent,
        I2cDeviceDeleteDialogComponent,
        I2cDeviceDeletePopupComponent,
    ],
    providers: [
        I2cDeviceService,
        I2cDevicePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoI2cDeviceModule {}
