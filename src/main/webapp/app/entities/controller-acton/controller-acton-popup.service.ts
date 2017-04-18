import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ControllerActon } from './controller-acton.model';
import { ControllerActonService } from './controller-acton.service';
@Injectable()
export class ControllerActonPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private controllerActonService: ControllerActonService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.controllerActonService.find(id).subscribe((controllerActon) => {
                controllerActon.actionStart = this.datePipe
                    .transform(controllerActon.actionStart, 'yyyy-MM-ddThh:mm');
                controllerActon.actionEnd = this.datePipe
                    .transform(controllerActon.actionEnd, 'yyyy-MM-ddThh:mm');
                this.controllerActonModalRef(component, controllerActon);
            });
        } else {
            return this.controllerActonModalRef(component, new ControllerActon());
        }
    }

    controllerActonModalRef(component: Component, controllerActon: ControllerActon): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.controllerActon = controllerActon;
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
