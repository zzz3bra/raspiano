import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Climate } from './climate.model';
import { ClimatePopupService } from './climate-popup.service';
import { ClimateService } from './climate.service';

@Component({
    selector: 'jhi-climate-delete-dialog',
    templateUrl: './climate-delete-dialog.component.html'
})
export class ClimateDeleteDialogComponent {

    climate: Climate;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private climateService: ClimateService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['climate']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.climateService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'climateListModification',
                content: 'Deleted an climate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-climate-delete-popup',
    template: ''
})
export class ClimateDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private climatePopupService: ClimatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.climatePopupService
                .open(ClimateDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
