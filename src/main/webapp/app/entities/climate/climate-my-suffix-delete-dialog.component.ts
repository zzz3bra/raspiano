import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ClimateMySuffix } from './climate-my-suffix.model';
import { ClimateMySuffixPopupService } from './climate-my-suffix-popup.service';
import { ClimateMySuffixService } from './climate-my-suffix.service';

@Component({
    selector: 'jhi-climate-my-suffix-delete-dialog',
    templateUrl: './climate-my-suffix-delete-dialog.component.html'
})
export class ClimateMySuffixDeleteDialogComponent {

    climate: ClimateMySuffix;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private climateService: ClimateMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['climate']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.climateService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'climateListModification',
                content: 'Deleted an climate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-climate-my-suffix-delete-popup',
    template: ''
})
export class ClimateMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private climatePopupService: ClimateMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.climatePopupService
                .open(ClimateMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
