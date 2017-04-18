import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { I2cSensor } from './i-2-c-sensor.model';
import { I2cSensorService } from './i-2-c-sensor.service';
@Injectable()
export class I2cSensorPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private i2cSensorService: I2cSensorService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.i2cSensorService.find(id).subscribe((i2cSensor) => {
                this.i2cSensorModalRef(component, i2cSensor);
            });
        } else {
            return this.i2cSensorModalRef(component, new I2cSensor());
        }
    }

    i2cSensorModalRef(component: Component, i2cSensor: I2cSensor): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.i2cSensor = i2cSensor;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
