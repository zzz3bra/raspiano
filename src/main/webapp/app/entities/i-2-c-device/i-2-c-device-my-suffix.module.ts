import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RaspianoSharedModule } from '../../shared';
import {
    I2cDeviceMySuffixService,
    I2cDeviceMySuffixPopupService,
    I2cDeviceMySuffixComponent,
    I2cDeviceMySuffixDetailComponent,
    I2cDeviceMySuffixDialogComponent,
    I2cDeviceMySuffixPopupComponent,
    I2cDeviceMySuffixDeletePopupComponent,
    I2cDeviceMySuffixDeleteDialogComponent,
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
        I2cDeviceMySuffixComponent,
        I2cDeviceMySuffixDetailComponent,
        I2cDeviceMySuffixDialogComponent,
        I2cDeviceMySuffixDeleteDialogComponent,
        I2cDeviceMySuffixPopupComponent,
        I2cDeviceMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        I2cDeviceMySuffixComponent,
        I2cDeviceMySuffixDialogComponent,
        I2cDeviceMySuffixPopupComponent,
        I2cDeviceMySuffixDeleteDialogComponent,
        I2cDeviceMySuffixDeletePopupComponent,
    ],
    providers: [
        I2cDeviceMySuffixService,
        I2cDeviceMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoI2cDeviceMySuffixModule {}
