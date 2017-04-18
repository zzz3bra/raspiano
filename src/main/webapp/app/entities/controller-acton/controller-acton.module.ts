import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RaspianoSharedModule } from '../../shared';
import {
    ControllerActonService,
    ControllerActonPopupService,
    ControllerActonComponent,
    ControllerActonDetailComponent,
    ControllerActonDialogComponent,
    ControllerActonPopupComponent,
    ControllerActonDeletePopupComponent,
    ControllerActonDeleteDialogComponent,
    controllerActonRoute,
    controllerActonPopupRoute,
} from './';

const ENTITY_STATES = [
    ...controllerActonRoute,
    ...controllerActonPopupRoute,
];

@NgModule({
    imports: [
        RaspianoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ControllerActonComponent,
        ControllerActonDetailComponent,
        ControllerActonDialogComponent,
        ControllerActonDeleteDialogComponent,
        ControllerActonPopupComponent,
        ControllerActonDeletePopupComponent,
    ],
    entryComponents: [
        ControllerActonComponent,
        ControllerActonDialogComponent,
        ControllerActonPopupComponent,
        ControllerActonDeleteDialogComponent,
        ControllerActonDeletePopupComponent,
    ],
    providers: [
        ControllerActonService,
        ControllerActonPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoControllerActonModule {}
