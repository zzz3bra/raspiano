import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { ControllerActon } from './controller-acton.model';
import { ControllerActonService } from './controller-acton.service';

@Component({
    selector: 'jhi-controller-acton-detail',
    templateUrl: './controller-acton-detail.component.html'
})
export class ControllerActonDetailComponent implements OnInit, OnDestroy {

    controllerActon: ControllerActon;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private controllerActonService: ControllerActonService,
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
