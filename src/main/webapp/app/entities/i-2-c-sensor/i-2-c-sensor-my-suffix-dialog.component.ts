import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { I2cSensorMySuffix } from './i-2-c-sensor-my-suffix.model';
import { I2cSensorMySuffixPopupService } from './i-2-c-sensor-my-suffix-popup.service';
import { I2cSensorMySuffixService } from './i-2-c-sensor-my-suffix.service';

@Component({
    selector: 'jhi-i-2-c-sensor-my-suffix-dialog',
    templateUrl: './i-2-c-sensor-my-suffix-dialog.component.html'
})
export class I2cSensorMySuffixDialogComponent implements OnInit {

    i2cSensor: I2cSensorMySuffix;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private i2cSensorService: I2cSensorMySuffixService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['i2cSensor', 'sensorType']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.i2cSensor.id !== undefined) {
            this.i2cSensorService.update(this.i2cSensor)
                .subscribe((res: I2cSensorMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.i2cSensorService.create(this.i2cSensor)
                .subscribe((res: I2cSensorMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: I2cSensorMySuffix) {
        this.eventManager.broadcast({ name: 'i2cSensorListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-i-2-c-sensor-my-suffix-popup',
    template: ''
})
export class I2cSensorMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private i2cSensorPopupService: I2cSensorMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.i2cSensorPopupService
                    .open(I2cSensorMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.i2cSensorPopupService
                    .open(I2cSensorMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
