import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { MeasurementMySuffix } from './measurement-my-suffix.model';
import { MeasurementMySuffixPopupService } from './measurement-my-suffix-popup.service';
import { MeasurementMySuffixService } from './measurement-my-suffix.service';
import { I2cSensorMySuffix, I2cSensorMySuffixService } from '../i-2-c-sensor';
import { ClimateMySuffix, ClimateMySuffixService } from '../climate';

@Component({
    selector: 'jhi-measurement-my-suffix-dialog',
    templateUrl: './measurement-my-suffix-dialog.component.html'
})
export class MeasurementMySuffixDialogComponent implements OnInit {

    measurement: MeasurementMySuffix;
    authorities: any[];
    isSaving: boolean;

    i2csensors: I2cSensorMySuffix[];

    climates: ClimateMySuffix[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private measurementService: MeasurementMySuffixService,
        private i2cSensorService: I2cSensorMySuffixService,
        private climateService: ClimateMySuffixService,
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
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.measurement.id !== undefined) {
            this.measurementService.update(this.measurement)
                .subscribe((res: MeasurementMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.measurementService.create(this.measurement)
                .subscribe((res: MeasurementMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: MeasurementMySuffix) {
        this.eventManager.broadcast({ name: 'measurementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackI2cSensorById(index: number, item: I2cSensorMySuffix) {
        return item.id;
    }

    trackClimateById(index: number, item: ClimateMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-measurement-my-suffix-popup',
    template: ''
})
export class MeasurementMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private measurementPopupService: MeasurementMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.measurementPopupService
                    .open(MeasurementMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.measurementPopupService
                    .open(MeasurementMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
