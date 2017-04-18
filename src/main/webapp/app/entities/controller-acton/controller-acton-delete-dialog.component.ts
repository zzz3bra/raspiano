import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ControllerActon } from './controller-acton.model';
import { ControllerActonPopupService } from './controller-acton-popup.service';
import { ControllerActonService } from './controller-acton.service';

@Component({
    selector: 'jhi-controller-acton-delete-dialog',
    templateUrl: './controller-acton-delete-dialog.component.html'
})
export class ControllerActonDeleteDialogComponent {

    controllerActon: ControllerActon;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private controllerActonService: ControllerActonService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['controllerActon']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.controllerActonService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'controllerActonListModification',
                content: 'Deleted an controllerActon'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-controller-acton-delete-popup',
    template: ''
})
export class ControllerActonDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private controllerActonPopupService: ControllerActonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.controllerActonPopupService
                .open(ControllerActonDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
