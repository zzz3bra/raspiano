import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RaspianoSharedModule } from '../../shared';

import {
    I2cControllerMySuffixService,
    I2cControllerMySuffixPopupService,
    I2cControllerMySuffixComponent,
    I2cControllerMySuffixDetailComponent,
    I2cControllerMySuffixDialogComponent,
    I2cControllerMySuffixPopupComponent,
    I2cControllerMySuffixDeletePopupComponent,
    I2cControllerMySuffixDeleteDialogComponent,
    i2cControllerRoute,
    i2cControllerPopupRoute,
} from './';

let ENTITY_STATES = [
    ...i2cControllerRoute,
    ...i2cControllerPopupRoute,
];

@NgModule({
    imports: [
        RaspianoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        I2cControllerMySuffixComponent,
        I2cControllerMySuffixDetailComponent,
        I2cControllerMySuffixDialogComponent,
        I2cControllerMySuffixDeleteDialogComponent,
        I2cControllerMySuffixPopupComponent,
        I2cControllerMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        I2cControllerMySuffixComponent,
        I2cControllerMySuffixDialogComponent,
        I2cControllerMySuffixPopupComponent,
        I2cControllerMySuffixDeleteDialogComponent,
        I2cControllerMySuffixDeletePopupComponent,
    ],
    providers: [
        I2cControllerMySuffixService,
        I2cControllerMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoI2cControllerMySuffixModule {}
