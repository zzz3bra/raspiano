import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RaspianoSharedModule } from '../../shared';

import {
    MeasurementMySuffixService,
    MeasurementMySuffixPopupService,
    MeasurementMySuffixComponent,
    MeasurementMySuffixDetailComponent,
    MeasurementMySuffixDialogComponent,
    MeasurementMySuffixPopupComponent,
    MeasurementMySuffixDeletePopupComponent,
    MeasurementMySuffixDeleteDialogComponent,
    measurementRoute,
    measurementPopupRoute,
} from './';

let ENTITY_STATES = [
    ...measurementRoute,
    ...measurementPopupRoute,
];

@NgModule({
    imports: [
        RaspianoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MeasurementMySuffixComponent,
        MeasurementMySuffixDetailComponent,
        MeasurementMySuffixDialogComponent,
        MeasurementMySuffixDeleteDialogComponent,
        MeasurementMySuffixPopupComponent,
        MeasurementMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        MeasurementMySuffixComponent,
        MeasurementMySuffixDialogComponent,
        MeasurementMySuffixPopupComponent,
        MeasurementMySuffixDeleteDialogComponent,
        MeasurementMySuffixDeletePopupComponent,
    ],
    providers: [
        MeasurementMySuffixService,
        MeasurementMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RaspianoMeasurementMySuffixModule {}
