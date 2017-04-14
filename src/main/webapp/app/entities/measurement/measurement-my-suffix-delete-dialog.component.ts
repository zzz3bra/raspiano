import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { MeasurementMySuffix } from './measurement-my-suffix.model';
import { MeasurementMySuffixPopupService } from './measurement-my-suffix-popup.service';
import { MeasurementMySuffixService } from './measurement-my-suffix.service';

@Component({
    selector: 'jhi-measurement-my-suffix-delete-dialog',
    templateUrl: './measurement-my-suffix-delete-dialog.component.html'
})
export class MeasurementMySuffixDeleteDialogComponent {

    measurement: MeasurementMySuffix;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private measurementService: MeasurementMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['measurement']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.measurementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'measurementListModification',
                content: 'Deleted an measurement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-measurement-my-suffix-delete-popup',
    template: ''
})
export class MeasurementMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private measurementPopupService: MeasurementMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.measurementPopupService
                .open(MeasurementMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
