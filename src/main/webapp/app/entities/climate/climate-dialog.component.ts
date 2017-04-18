import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Climate } from './climate.model';
import { ClimatePopupService } from './climate-popup.service';
import { ClimateService } from './climate.service';

@Component({
    selector: 'jhi-climate-dialog',
    templateUrl: './climate-dialog.component.html'
})
export class ClimateDialogComponent implements OnInit {

    climate: Climate;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private climateService: ClimateService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['climate']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.climate.id !== undefined) {
            this.climateService.update(this.climate)
                .subscribe((res: Climate) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.climateService.create(this.climate)
                .subscribe((res: Climate) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Climate) {
        this.eventManager.broadcast({ name: 'climateListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-climate-popup',
    template: ''
})
export class ClimatePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private climatePopupService: ClimatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.climatePopupService
                    .open(ClimateDialogComponent, params['id']);
            } else {
                this.modalRef = this.climatePopupService
                    .open(ClimateDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
