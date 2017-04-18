import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Raspberry } from './raspberry.model';
import { RaspberryPopupService } from './raspberry-popup.service';
import { RaspberryService } from './raspberry.service';
import { Climate, ClimateService } from '../climate';

@Component({
    selector: 'jhi-raspberry-dialog',
    templateUrl: './raspberry-dialog.component.html'
})
export class RaspberryDialogComponent implements OnInit {

    raspberry: Raspberry;
    authorities: any[];
    isSaving: boolean;

    climates: Climate[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private raspberryService: RaspberryService,
        private climateService: ClimateService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['raspberry']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.climateService.query({filter: 'raspberry-is-null'}).subscribe((res: Response) => {
            if (!this.raspberry.climateId) {
                this.climates = res.json();
            } else {
                this.climateService.find(this.raspberry.climateId).subscribe((subRes: Climate) => {
                    this.climates = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.raspberry.id !== undefined) {
            this.raspberryService.update(this.raspberry)
                .subscribe((res: Raspberry) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.raspberryService.create(this.raspberry)
                .subscribe((res: Raspberry) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Raspberry) {
        this.eventManager.broadcast({ name: 'raspberryListModification', content: 'OK'});
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

    trackClimateById(index: number, item: Climate) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-raspberry-popup',
    template: ''
})
export class RaspberryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private raspberryPopupService: RaspberryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.raspberryPopupService
                    .open(RaspberryDialogComponent, params['id']);
            } else {
                this.modalRef = this.raspberryPopupService
                    .open(RaspberryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
