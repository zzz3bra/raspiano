import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { RaspberryMySuffix } from './raspberry-my-suffix.model';
import { RaspberryMySuffixPopupService } from './raspberry-my-suffix-popup.service';
import { RaspberryMySuffixService } from './raspberry-my-suffix.service';

@Component({
    selector: 'jhi-raspberry-my-suffix-delete-dialog',
    templateUrl: './raspberry-my-suffix-delete-dialog.component.html'
})
export class RaspberryMySuffixDeleteDialogComponent {

    raspberry: RaspberryMySuffix;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private raspberryService: RaspberryMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['raspberry']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.raspberryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'raspberryListModification',
                content: 'Deleted an raspberry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-raspberry-my-suffix-delete-popup',
    template: ''
})
export class RaspberryMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private raspberryPopupService: RaspberryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.raspberryPopupService
                .open(RaspberryMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
