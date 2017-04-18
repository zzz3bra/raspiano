import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ControllerActon } from './controller-acton.model';
import { ControllerActonPopupService } from './controller-acton-popup.service';
import { ControllerActonService } from './controller-acton.service';
import { I2cController, I2cControllerService } from '../i-2-c-controller';
import { Climate, ClimateService } from '../climate';

@Component({
    selector: 'jhi-controller-acton-dialog',
    templateUrl: './controller-acton-dialog.component.html'
})
export class ControllerActonDialogComponent implements OnInit {

    controllerActon: ControllerActon;
    authorities: any[];
    isSaving: boolean;

    i2ccontrollers: I2cController[];

    climates: Climate[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private controllerActonService: ControllerActonService,
        private i2cControllerService: I2cControllerService,
        private climateService: ClimateService,
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
                .subscribe((res: ControllerActon) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.controllerActonService.create(this.controllerActon)
                .subscribe((res: ControllerActon) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ControllerActon) {
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

    trackI2cControllerById(index: number, item: I2cController) {
        return item.id;
    }

    trackClimateById(index: number, item: Climate) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-controller-acton-popup',
    template: ''
})
export class ControllerActonPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private controllerActonPopupService: ControllerActonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.controllerActonPopupService
                    .open(ControllerActonDialogComponent, params['id']);
            } else {
                this.modalRef = this.controllerActonPopupService
                    .open(ControllerActonDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
