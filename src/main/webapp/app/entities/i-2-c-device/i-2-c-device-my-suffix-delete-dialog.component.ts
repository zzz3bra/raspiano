import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { I2cDeviceMySuffix } from './i-2-c-device-my-suffix.model';
import { I2cDeviceMySuffixPopupService } from './i-2-c-device-my-suffix-popup.service';
import { I2cDeviceMySuffixService } from './i-2-c-device-my-suffix.service';

@Component({
    selector: 'jhi-i-2-c-device-my-suffix-delete-dialog',
    templateUrl: './i-2-c-device-my-suffix-delete-dialog.component.html'
})
export class I2cDeviceMySuffixDeleteDialogComponent {

    i2cDevice: I2cDeviceMySuffix;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private i2cDeviceService: I2cDeviceMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['i2cDevice', 'deviceType']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.i2cDeviceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'i2cDeviceListModification',
                content: 'Deleted an i2cDevice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-i-2-c-device-my-suffix-delete-popup',
    template: ''
})
export class I2cDeviceMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private i2cDevicePopupService: I2cDeviceMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.i2cDevicePopupService
                .open(I2cDeviceMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
