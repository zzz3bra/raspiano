import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { I2cController } from './i-2-c-controller.model';
import { I2cControllerService } from './i-2-c-controller.service';
@Injectable()
export class I2cControllerPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private i2cControllerService: I2cControllerService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.i2cControllerService.find(id).subscribe((i2cController) => {
                this.i2cControllerModalRef(component, i2cController);
            });
        } else {
            return this.i2cControllerModalRef(component, new I2cController());
        }
    }

    i2cControllerModalRef(component: Component, i2cController: I2cController): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.i2cController = i2cController;
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
