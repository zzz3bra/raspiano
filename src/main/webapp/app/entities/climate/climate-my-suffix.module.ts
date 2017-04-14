import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RaspianoSharedModule } from '../../shared';

import {
    ClimateMySuffixService,
    ClimateMySuffixPopupService,
    ClimateMySuffixComponent,
    ClimateMySuffixDetailComponent,
    ClimateMySuffixDialogComponent,
    ClimateMySuffixPopupComponent,
    ClimateMySuffixDeletePopupComponent,
    ClimateMySuffixDeleteDialogComponent,
    climateRoute,
    climatePopupRoute,
} from './';

let ENTITY_STATES = [
    ...climateRoute,
    ...climatePopupRoute,
];

@NgModule({
    imports: [
        RaspianoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClimateMySuffixComponent,
        ClimateMySuffixDetailComponent,
        ClimateMySuffixDialogComponent,
        ClimateMySuffixDeleteDialogComponent,
        ClimateMySuffixPopupComponent,
        ClimateMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ClimateMySuffixComponent,
        ClimateMySuffixDialogComponent,
        ClimateMySuffixPopupComponent,
        ClimateMySuffixDeleteDialogComponent,
        ClimateMySuffixDeletePopupComponent,
    ],
    providers: [
        ClimateMySuffixService,
        ClimateMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoClimateMySuffixModule {}
