import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ControllerActonMySuffix } from './controller-acton-my-suffix.model';
import { ControllerActonMySuffixService } from './controller-acton-my-suffix.service';
@Injectable()
export class ControllerActonMySuffixPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private controllerActonService: ControllerActonMySuffixService

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
            return this.controllerActonModalRef(component, new ControllerActonMySuffix());
        }
    }

    controllerActonModalRef(component: Component, controllerActon: ControllerActonMySuffix): NgbModalRef {
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
