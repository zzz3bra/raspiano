import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { I2cControllerMySuffix } from './i-2-c-controller-my-suffix.model';
import { I2cControllerMySuffixPopupService } from './i-2-c-controller-my-suffix-popup.service';
import { I2cControllerMySuffixService } from './i-2-c-controller-my-suffix.service';

@Component({
    selector: 'jhi-i-2-c-controller-my-suffix-dialog',
    templateUrl: './i-2-c-controller-my-suffix-dialog.component.html'
})
export class I2cControllerMySuffixDialogComponent implements OnInit {

    i2cController: I2cControllerMySuffix;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private i2cControllerService: I2cControllerMySuffixService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['i2cController', 'controllerType']);
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
        if (this.i2cController.id !== undefined) {
            this.i2cControllerService.update(this.i2cController)
                .subscribe((res: I2cControllerMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.i2cControllerService.create(this.i2cController)
                .subscribe((res: I2cControllerMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: I2cControllerMySuffix) {
        this.eventManager.broadcast({ name: 'i2cControllerListModification', content: 'OK'});
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
    selector: 'jhi-i-2-c-controller-my-suffix-popup',
    template: ''
})
export class I2cControllerMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private i2cControllerPopupService: I2cControllerMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.i2cControllerPopupService
                    .open(I2cControllerMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.i2cControllerPopupService
                    .open(I2cControllerMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
