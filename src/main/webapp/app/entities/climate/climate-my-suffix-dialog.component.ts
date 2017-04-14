import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ClimateMySuffix } from './climate-my-suffix.model';
import { ClimateMySuffixPopupService } from './climate-my-suffix-popup.service';
import { ClimateMySuffixService } from './climate-my-suffix.service';

@Component({
    selector: 'jhi-climate-my-suffix-dialog',
    templateUrl: './climate-my-suffix-dialog.component.html'
})
export class ClimateMySuffixDialogComponent implements OnInit {

    climate: ClimateMySuffix;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private climateService: ClimateMySuffixService,
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
                .subscribe((res: ClimateMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.climateService.create(this.climate)
                .subscribe((res: ClimateMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ClimateMySuffix) {
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
    selector: 'jhi-climate-my-suffix-popup',
    template: ''
})
export class ClimateMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private climatePopupService: ClimateMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.climatePopupService
                    .open(ClimateMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.climatePopupService
                    .open(ClimateMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
