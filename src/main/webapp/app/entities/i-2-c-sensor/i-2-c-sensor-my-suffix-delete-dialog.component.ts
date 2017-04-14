import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { I2cSensorMySuffix } from './i-2-c-sensor-my-suffix.model';
import { I2cSensorMySuffixPopupService } from './i-2-c-sensor-my-suffix-popup.service';
import { I2cSensorMySuffixService } from './i-2-c-sensor-my-suffix.service';

@Component({
    selector: 'jhi-i-2-c-sensor-my-suffix-delete-dialog',
    templateUrl: './i-2-c-sensor-my-suffix-delete-dialog.component.html'
})
export class I2cSensorMySuffixDeleteDialogComponent {

    i2cSensor: I2cSensorMySuffix;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private i2cSensorService: I2cSensorMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['i2cSensor', 'sensorType']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.i2cSensorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'i2cSensorListModification',
                content: 'Deleted an i2cSensor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-i-2-c-sensor-my-suffix-delete-popup',
    template: ''
})
export class I2cSensorMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private i2cSensorPopupService: I2cSensorMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.i2cSensorPopupService
                .open(I2cSensorMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
