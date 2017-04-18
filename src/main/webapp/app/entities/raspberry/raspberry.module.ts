import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RaspianoSharedModule } from '../../shared';
import {
    RaspberryService,
    RaspberryPopupService,
    RaspberryComponent,
    RaspberryDetailComponent,
    RaspberryDialogComponent,
    RaspberryPopupComponent,
    RaspberryDeletePopupComponent,
    RaspberryDeleteDialogComponent,
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
        RaspberryComponent,
        RaspberryDetailComponent,
        RaspberryDialogComponent,
        RaspberryDeleteDialogComponent,
        RaspberryPopupComponent,
        RaspberryDeletePopupComponent,
    ],
    entryComponents: [
        RaspberryComponent,
        RaspberryDialogComponent,
        RaspberryPopupComponent,
        RaspberryDeleteDialogComponent,
        RaspberryDeletePopupComponent,
    ],
    providers: [
        RaspberryService,
        RaspberryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoRaspberryModule {}
