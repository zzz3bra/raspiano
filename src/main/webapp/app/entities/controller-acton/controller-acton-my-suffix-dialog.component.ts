import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ControllerActonMySuffix } from './controller-acton-my-suffix.model';
import { ControllerActonMySuffixPopupService } from './controller-acton-my-suffix-popup.service';
import { ControllerActonMySuffixService } from './controller-acton-my-suffix.service';
import { I2cControllerMySuffix, I2cControllerMySuffixService } from '../i-2-c-controller';
import { ClimateMySuffix, ClimateMySuffixService } from '../climate';

@Component({
    selector: 'jhi-controller-acton-my-suffix-dialog',
    templateUrl: './controller-acton-my-suffix-dialog.component.html'
})
export class ControllerActonMySuffixDialogComponent implements OnInit {

    controllerActon: ControllerActonMySuffix;
    authorities: any[];
    isSaving: boolean;

    i2ccontrollers: I2cControllerMySuffix[];

    climates: ClimateMySuffix[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private controllerActonService: ControllerActonMySuffixService,
        private i2cControllerService: I2cControllerMySuffixService,
        private climateService: ClimateMySuffixService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['controllerActon']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.i2cControllerService.query().subscribe(
            (res: Response) => { this.i2ccontrollers = res.json(); }, (res: Response) => this.onError(res.json()));
        this.climateService.query().subscribe(
            (res: Response) => { this.climates = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.controllerActon.id !== undefined) {
            this.controllerActonService.update(this.controllerActon)
                .subscribe((res: ControllerActonMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.controllerActonService.create(this.controllerActon)
                .subscribe((res: ControllerActonMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ControllerActonMySuffix) {
        this.eventManager.broadcast({ name: 'controllerActonListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackI2cControllerById(index: number, item: I2cControllerMySuffix) {
        return item.id;
    }

    trackClimateById(index: number, item: ClimateMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-controller-acton-my-suffix-popup',
    template: ''
})
export class ControllerActonMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private controllerActonPopupService: ControllerActonMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.controllerActonPopupService
                    .open(ControllerActonMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.controllerActonPopupService
                    .open(ControllerActonMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
