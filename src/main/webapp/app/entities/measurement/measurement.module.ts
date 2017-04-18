import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RaspianoSharedModule } from '../../shared';
import {
    MeasurementService,
    MeasurementPopupService,
    MeasurementComponent,
    MeasurementDetailComponent,
    MeasurementDialogComponent,
    MeasurementPopupComponent,
    MeasurementDeletePopupComponent,
    MeasurementDeleteDialogComponent,
    measurementRoute,
    measurementPopupRoute,
} from './';

const ENTITY_STATES = [
    ...measurementRoute,
    ...measurementPopupRoute,
];

@NgModule({
    imports: [
        RaspianoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MeasurementComponent,
        MeasurementDetailComponent,
        MeasurementDialogComponent,
        MeasurementDeleteDialogComponent,
        MeasurementPopupComponent,
        MeasurementDeletePopupComponent,
    ],
    entryComponents: [
        MeasurementComponent,
        MeasurementDialogComponent,
        MeasurementPopupComponent,
        MeasurementDeleteDialogComponent,
        MeasurementDeletePopupComponent,
    ],
    providers: [
        MeasurementService,
        MeasurementPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoMeasurementModule {}
