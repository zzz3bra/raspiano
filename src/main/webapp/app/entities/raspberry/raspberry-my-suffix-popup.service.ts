import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RaspberryMySuffix } from './raspberry-my-suffix.model';
import { RaspberryMySuffixService } from './raspberry-my-suffix.service';
@Injectable()
export class RaspberryMySuffixPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private raspberryService: RaspberryMySuffixService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.raspberryService.find(id).subscribe(raspberry => {
                this.raspberryModalRef(component, raspberry);
            });
        } else {
            return this.raspberryModalRef(component, new RaspberryMySuffix());
        }
    }

    raspberryModalRef(component: Component, raspberry: RaspberryMySuffix): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.raspberry = raspberry;
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
