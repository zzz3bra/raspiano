import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { I2cSensor } from './i-2-c-sensor.model';
import { I2cSensorPopupService } from './i-2-c-sensor-popup.service';
import { I2cSensorService } from './i-2-c-sensor.service';

@Component({
    selector: 'jhi-i-2-c-sensor-delete-dialog',
    templateUrl: './i-2-c-sensor-delete-dialog.component.html'
})
export class I2cSensorDeleteDialogComponent {

    i2cSensor: I2cSensor;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private i2cSensorService: I2cSensorService,
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
    selector: 'jhi-i-2-c-sensor-delete-popup',
    template: ''
})
export class I2cSensorDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private i2cSensorPopupService: I2cSensorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.i2cSensorPopupService
                .open(I2cSensorDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
