import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { I2cSensor } from './i-2-c-sensor.model';
import { I2cSensorPopupService } from './i-2-c-sensor-popup.service';
import { I2cSensorService } from './i-2-c-sensor.service';
import { I2cDevice, I2cDeviceService } from '../i-2-c-device';

@Component({
    selector: 'jhi-i-2-c-sensor-dialog',
    templateUrl: './i-2-c-sensor-dialog.component.html'
})
export class I2cSensorDialogComponent implements OnInit {

    i2cSensor: I2cSensor;
    authorities: any[];
    isSaving: boolean;

    i2cdevices: I2cDevice[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private i2cSensorService: I2cSensorService,
        private i2cDeviceService: I2cDeviceService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['i2cSensor', 'sensorType']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.i2cDeviceService.query().subscribe(
            (res: Response) => { this.i2cdevices = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.i2cSensor.id !== undefined) {
            this.i2cSensorService.update(this.i2cSensor)
                .subscribe((res: I2cSensor) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.i2cSensorService.create(this.i2cSensor)
                .subscribe((res: I2cSensor) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: I2cSensor) {
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

    trackI2cDeviceById(index: number, item: I2cDevice) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-i-2-c-sensor-popup',
    template: ''
})
export class I2cSensorPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private i2cSensorPopupService: I2cSensorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.i2cSensorPopupService
                    .open(I2cSensorDialogComponent, params['id']);
            } else {
                this.modalRef = this.i2cSensorPopupService
                    .open(I2cSensorDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
