import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RaspianoSharedModule } from '../../shared';
import {
    ClimateService,
    ClimatePopupService,
    ClimateComponent,
    ClimateDetailComponent,
    ClimateDialogComponent,
    ClimatePopupComponent,
    ClimateDeletePopupComponent,
    ClimateDeleteDialogComponent,
    climateRoute,
    climatePopupRoute,
} from './';

const ENTITY_STATES = [
    ...climateRoute,
    ...climatePopupRoute,
];

@NgModule({
    imports: [
        RaspianoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClimateComponent,
        ClimateDetailComponent,
        ClimateDialogComponent,
        ClimateDeleteDialogComponent,
        ClimatePopupComponent,
        ClimateDeletePopupComponent,
    ],
    entryComponents: [
        ClimateComponent,
        ClimateDialogComponent,
        ClimatePopupComponent,
        ClimateDeleteDialogComponent,
        ClimateDeletePopupComponent,
    ],
    providers: [
        ClimateService,
        ClimatePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoClimateModule {}
