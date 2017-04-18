import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RaspianoSharedModule } from '../../shared';
import {
    I2cControllerService,
    I2cControllerPopupService,
    I2cControllerComponent,
    I2cControllerDetailComponent,
    I2cControllerDialogComponent,
    I2cControllerPopupComponent,
    I2cControllerDeletePopupComponent,
    I2cControllerDeleteDialogComponent,
    i2cControllerRoute,
    i2cControllerPopupRoute,
} from './';

const ENTITY_STATES = [
    ...i2cControllerRoute,
    ...i2cControllerPopupRoute,
];

@NgModule({
    imports: [
        RaspianoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        I2cControllerComponent,
        I2cControllerDetailComponent,
        I2cControllerDialogComponent,
        I2cControllerDeleteDialogComponent,
        I2cControllerPopupComponent,
        I2cControllerDeletePopupComponent,
    ],
    entryComponents: [
        I2cControllerComponent,
        I2cControllerDialogComponent,
        I2cControllerPopupComponent,
        I2cControllerDeleteDialogComponent,
        I2cControllerDeletePopupComponent,
    ],
    providers: [
        I2cControllerService,
        I2cControllerPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoI2cControllerModule {}
