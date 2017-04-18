import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { I2cDevice } from './i-2-c-device.model';
import { I2cDevicePopupService } from './i-2-c-device-popup.service';
import { I2cDeviceService } from './i-2-c-device.service';

@Component({
    selector: 'jhi-i-2-c-device-delete-dialog',
    templateUrl: './i-2-c-device-delete-dialog.component.html'
})
export class I2cDeviceDeleteDialogComponent {

    i2cDevice: I2cDevice;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private i2cDeviceService: I2cDeviceService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['i2cDevice', 'deviceType']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.i2cDeviceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'i2cDeviceListModification',
                content: 'Deleted an i2cDevice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-i-2-c-device-delete-popup',
    template: ''
})
export class I2cDeviceDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private i2cDevicePopupService: I2cDevicePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.i2cDevicePopupService
                .open(I2cDeviceDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
