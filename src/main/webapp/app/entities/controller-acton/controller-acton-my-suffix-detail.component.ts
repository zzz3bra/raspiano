import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { ControllerActonMySuffix } from './controller-acton-my-suffix.model';
import { ControllerActonMySuffixService } from './controller-acton-my-suffix.service';

@Component({
    selector: 'jhi-controller-acton-my-suffix-detail',
    templateUrl: './controller-acton-my-suffix-detail.component.html'
})
export class ControllerActonMySuffixDetailComponent implements OnInit, OnDestroy {

    controllerActon: ControllerActonMySuffix;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private controllerActonService: ControllerActonMySuffixService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['controllerActon']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInControllerActons();
    }

    load(id) {
        this.controllerActonService.find(id).subscribe((controllerActon) => {
            this.controllerActon = controllerActon;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInControllerActons() {
        this.eventSubscriber = this.eventManager.subscribe('controllerActonListModification', (response) => this.load(this.controllerActon.id));
    }
}
