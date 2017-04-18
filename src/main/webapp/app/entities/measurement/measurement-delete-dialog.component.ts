import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Measurement } from './measurement.model';
import { MeasurementPopupService } from './measurement-popup.service';
import { MeasurementService } from './measurement.service';

@Component({
    selector: 'jhi-measurement-delete-dialog',
    templateUrl: './measurement-delete-dialog.component.html'
})
export class MeasurementDeleteDialogComponent {

    measurement: Measurement;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private measurementService: MeasurementService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['measurement']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.measurementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'measurementListModification',
                content: 'Deleted an measurement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-measurement-delete-popup',
    template: ''
})
export class MeasurementDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private measurementPopupService: MeasurementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.measurementPopupService
                .open(MeasurementDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
