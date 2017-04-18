import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Measurement } from './measurement.model';
import { MeasurementPopupService } from './measurement-popup.service';
import { MeasurementService } from './measurement.service';
import { I2cSensor, I2cSensorService } from '../i-2-c-sensor';
import { Climate, ClimateService } from '../climate';

@Component({
    selector: 'jhi-measurement-dialog',
    templateUrl: './measurement-dialog.component.html'
})
export class MeasurementDialogComponent implements OnInit {

    measurement: Measurement;
    authorities: any[];
    isSaving: boolean;

    i2csensors: I2cSensor[];

    climates: Climate[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private measurementService: MeasurementService,
        private i2cSensorService: I2cSensorService,
        private climateService: ClimateService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['measurement']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.i2cSensorService.query().subscribe(
            (res: Response) => { this.i2csensors = res.json(); }, (res: Response) => this.onError(res.json()));
        this.climateService.query().subscribe(
            (res: Response) => { this.climates = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.measurement.id !== undefined) {
            this.measurementService.update(this.measurement)
                .subscribe((res: Measurement) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.measurementService.create(this.measurement)
                .subscribe((res: Measurement) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Measurement) {
        this.eventManager.broadcast({ name: 'measurementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackI2cSensorById(index: number, item: I2cSensor) {
        return item.id;
    }

    trackClimateById(index: number, item: Climate) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-measurement-popup',
    template: ''
})
export class MeasurementPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private measurementPopupService: MeasurementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.measurementPopupService
                    .open(MeasurementDialogComponent, params['id']);
            } else {
                this.modalRef = this.measurementPopupService
                    .open(MeasurementDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
