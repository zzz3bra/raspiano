import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ControllerActonMySuffix } from './controller-acton-my-suffix.model';
import { ControllerActonMySuffixPopupService } from './controller-acton-my-suffix-popup.service';
import { ControllerActonMySuffixService } from './controller-acton-my-suffix.service';

@Component({
    selector: 'jhi-controller-acton-my-suffix-delete-dialog',
    templateUrl: './controller-acton-my-suffix-delete-dialog.component.html'
})
export class ControllerActonMySuffixDeleteDialogComponent {

    controllerActon: ControllerActonMySuffix;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private controllerActonService: ControllerActonMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['controllerActon']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.controllerActonService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'controllerActonListModification',
                content: 'Deleted an controllerActon'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-controller-acton-my-suffix-delete-popup',
    template: ''
})
export class ControllerActonMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private controllerActonPopupService: ControllerActonMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.controllerActonPopupService
                .open(ControllerActonMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
