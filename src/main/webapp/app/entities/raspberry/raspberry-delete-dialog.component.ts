import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Raspberry } from './raspberry.model';
import { RaspberryPopupService } from './raspberry-popup.service';
import { RaspberryService } from './raspberry.service';

@Component({
    selector: 'jhi-raspberry-delete-dialog',
    templateUrl: './raspberry-delete-dialog.component.html'
})
export class RaspberryDeleteDialogComponent {

    raspberry: Raspberry;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private raspberryService: RaspberryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['raspberry']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.raspberryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'raspberryListModification',
                content: 'Deleted an raspberry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-raspberry-delete-popup',
    template: ''
})
export class RaspberryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private raspberryPopupService: RaspberryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.raspberryPopupService
                .open(RaspberryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
