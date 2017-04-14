import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { I2cDeviceMySuffix } from './i-2-c-device-my-suffix.model';
import { I2cDeviceMySuffixPopupService } from './i-2-c-device-my-suffix-popup.service';
import { I2cDeviceMySuffixService } from './i-2-c-device-my-suffix.service';
import { RaspberryMySuffix, RaspberryMySuffixService } from '../raspberry';

@Component({
    selector: 'jhi-i-2-c-device-my-suffix-dialog',
    templateUrl: './i-2-c-device-my-suffix-dialog.component.html'
})
export class I2cDeviceMySuffixDialogComponent implements OnInit {

    i2cDevice: I2cDeviceMySuffix;
    authorities: any[];
    isSaving: boolean;

    raspberries: RaspberryMySuffix[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private i2cDeviceService: I2cDeviceMySuffixService,
        private raspberryService: RaspberryMySuffixService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['i2cDevice', 'deviceType']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.raspberryService.query().subscribe(
            (res: Response) => { this.raspberries = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.i2cDevice.id !== undefined) {
            this.i2cDeviceService.update(this.i2cDevice)
                .subscribe((res: I2cDeviceMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.i2cDeviceService.create(this.i2cDevice)
                .subscribe((res: I2cDeviceMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: I2cDeviceMySuffix) {
        this.eventManager.broadcast({ name: 'i2cDeviceListModification', content: 'OK'});
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

    trackRaspberryById(index: number, item: RaspberryMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-i-2-c-device-my-suffix-popup',
    template: ''
})
export class I2cDeviceMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private i2cDevicePopupService: I2cDeviceMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.i2cDevicePopupService
                    .open(I2cDeviceMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.i2cDevicePopupService
                    .open(I2cDeviceMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
