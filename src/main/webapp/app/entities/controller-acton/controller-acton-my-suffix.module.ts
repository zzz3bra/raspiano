import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RaspianoSharedModule } from '../../shared';
import {
    ControllerActonMySuffixService,
    ControllerActonMySuffixPopupService,
    ControllerActonMySuffixComponent,
    ControllerActonMySuffixDetailComponent,
    ControllerActonMySuffixDialogComponent,
    ControllerActonMySuffixPopupComponent,
    ControllerActonMySuffixDeletePopupComponent,
    ControllerActonMySuffixDeleteDialogComponent,
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
        ControllerActonMySuffixComponent,
        ControllerActonMySuffixDetailComponent,
        ControllerActonMySuffixDialogComponent,
        ControllerActonMySuffixDeleteDialogComponent,
        ControllerActonMySuffixPopupComponent,
        ControllerActonMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ControllerActonMySuffixComponent,
        ControllerActonMySuffixDialogComponent,
        ControllerActonMySuffixPopupComponent,
        ControllerActonMySuffixDeleteDialogComponent,
        ControllerActonMySuffixDeletePopupComponent,
    ],
    providers: [
        ControllerActonMySuffixService,
        ControllerActonMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoControllerActonMySuffixModule {}
