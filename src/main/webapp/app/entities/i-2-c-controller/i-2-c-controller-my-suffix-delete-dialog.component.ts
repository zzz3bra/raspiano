import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { I2cControllerMySuffix } from './i-2-c-controller-my-suffix.model';
import { I2cControllerMySuffixPopupService } from './i-2-c-controller-my-suffix-popup.service';
import { I2cControllerMySuffixService } from './i-2-c-controller-my-suffix.service';

@Component({
    selector: 'jhi-i-2-c-controller-my-suffix-delete-dialog',
    templateUrl: './i-2-c-controller-my-suffix-delete-dialog.component.html'
})
export class I2cControllerMySuffixDeleteDialogComponent {

    i2cController: I2cControllerMySuffix;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private i2cControllerService: I2cControllerMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['i2cController', 'controllerType']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.i2cControllerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'i2cControllerListModification',
                content: 'Deleted an i2cController'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-i-2-c-controller-my-suffix-delete-popup',
    template: ''
})
export class I2cControllerMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private i2cControllerPopupService: I2cControllerMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.i2cControllerPopupService
                .open(I2cControllerMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
