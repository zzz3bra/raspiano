import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { I2cController } from './i-2-c-controller.model';
import { I2cControllerPopupService } from './i-2-c-controller-popup.service';
import { I2cControllerService } from './i-2-c-controller.service';
import { I2cDevice, I2cDeviceService } from '../i-2-c-device';

@Component({
    selector: 'jhi-i-2-c-controller-dialog',
    templateUrl: './i-2-c-controller-dialog.component.html'
})
export class I2cControllerDialogComponent implements OnInit {

    i2cController: I2cController;
    authorities: any[];
    isSaving: boolean;

    i2cdevices: I2cDevice[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private i2cControllerService: I2cControllerService,
        private i2cDeviceService: I2cDeviceService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['i2cController', 'controllerType']);
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
        if (this.i2cController.id !== undefined) {
            this.i2cControllerService.update(this.i2cController)
                .subscribe((res: I2cController) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.i2cControllerService.create(this.i2cController)
                .subscribe((res: I2cController) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: I2cController) {
        this.eventManager.broadcast({ name: 'i2cControllerListModification', content: 'OK'});
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
    selector: 'jhi-i-2-c-controller-popup',
    template: ''
})
export class I2cControllerPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private i2cControllerPopupService: I2cControllerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.i2cControllerPopupService
                    .open(I2cControllerDialogComponent, params['id']);
            } else {
                this.modalRef = this.i2cControllerPopupService
                    .open(I2cControllerDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
