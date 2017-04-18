import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { I2cController } from './i-2-c-controller.model';
import { I2cControllerPopupService } from './i-2-c-controller-popup.service';
import { I2cControllerService } from './i-2-c-controller.service';

@Component({
    selector: 'jhi-i-2-c-controller-delete-dialog',
    templateUrl: './i-2-c-controller-delete-dialog.component.html'
})
export class I2cControllerDeleteDialogComponent {

    i2cController: I2cController;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private i2cControllerService: I2cControllerService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['i2cController', 'controllerType']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.i2cControllerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'i2cControllerListModification',
                content: 'Deleted an i2cController'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-i-2-c-controller-delete-popup',
    template: ''
})
export class I2cControllerDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private i2cControllerPopupService: I2cControllerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.i2cControllerPopupService
                .open(I2cControllerDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
