import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { I2cDeviceMySuffix } from './i-2-c-device-my-suffix.model';
import { I2cDeviceMySuffixService } from './i-2-c-device-my-suffix.service';
@Injectable()
export class I2cDeviceMySuffixPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private i2cDeviceService: I2cDeviceMySuffixService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.i2cDeviceService.find(id).subscribe(i2cDevice => {
                this.i2cDeviceModalRef(component, i2cDevice);
            });
        } else {
            return this.i2cDeviceModalRef(component, new I2cDeviceMySuffix());
        }
    }

    i2cDeviceModalRef(component: Component, i2cDevice: I2cDeviceMySuffix): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.i2cDevice = i2cDevice;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
