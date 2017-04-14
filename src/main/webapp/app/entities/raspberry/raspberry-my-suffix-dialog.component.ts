import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { RaspberryMySuffix } from './raspberry-my-suffix.model';
import { RaspberryMySuffixPopupService } from './raspberry-my-suffix-popup.service';
import { RaspberryMySuffixService } from './raspberry-my-suffix.service';

@Component({
    selector: 'jhi-raspberry-my-suffix-dialog',
    templateUrl: './raspberry-my-suffix-dialog.component.html'
})
export class RaspberryMySuffixDialogComponent implements OnInit {

    raspberry: RaspberryMySuffix;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private raspberryService: RaspberryMySuffixService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['raspberry']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.raspberry.id !== undefined) {
            this.raspberryService.update(this.raspberry)
                .subscribe((res: RaspberryMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.raspberryService.create(this.raspberry)
                .subscribe((res: RaspberryMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: RaspberryMySuffix) {
        this.eventManager.broadcast({ name: 'raspberryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-raspberry-my-suffix-popup',
    template: ''
})
export class RaspberryMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private raspberryPopupService: RaspberryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.raspberryPopupService
                    .open(RaspberryMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.raspberryPopupService
                    .open(RaspberryMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
