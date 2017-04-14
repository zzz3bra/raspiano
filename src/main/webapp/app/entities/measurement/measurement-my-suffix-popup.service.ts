import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { MeasurementMySuffix } from './measurement-my-suffix.model';
import { MeasurementMySuffixService } from './measurement-my-suffix.service';
@Injectable()
export class MeasurementMySuffixPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private measurementService: MeasurementMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.measurementService.find(id).subscribe((measurement) => {
                measurement.dateTime = this.datePipe
                    .transform(measurement.dateTime, 'yyyy-MM-ddThh:mm');
                this.measurementModalRef(component, measurement);
            });
        } else {
            return this.measurementModalRef(component, new MeasurementMySuffix());
        }
    }

    measurementModalRef(component: Component, measurement: MeasurementMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.measurement = measurement;
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
