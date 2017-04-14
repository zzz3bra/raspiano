import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RaspianoSharedModule } from '../../shared';
import {
    RaspberryMySuffixService,
    RaspberryMySuffixPopupService,
    RaspberryMySuffixComponent,
    RaspberryMySuffixDetailComponent,
    RaspberryMySuffixDialogComponent,
    RaspberryMySuffixPopupComponent,
    RaspberryMySuffixDeletePopupComponent,
    RaspberryMySuffixDeleteDialogComponent,
    raspberryRoute,
    raspberryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...raspberryRoute,
    ...raspberryPopupRoute,
];

@NgModule({
    imports: [
        RaspianoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RaspberryMySuffixComponent,
        RaspberryMySuffixDetailComponent,
        RaspberryMySuffixDialogComponent,
        RaspberryMySuffixDeleteDialogComponent,
        RaspberryMySuffixPopupComponent,
        RaspberryMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        RaspberryMySuffixComponent,
        RaspberryMySuffixDialogComponent,
        RaspberryMySuffixPopupComponent,
        RaspberryMySuffixDeleteDialogComponent,
        RaspberryMySuffixDeletePopupComponent,
    ],
    providers: [
        RaspberryMySuffixService,
        RaspberryMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoRaspberryMySuffixModule {}
