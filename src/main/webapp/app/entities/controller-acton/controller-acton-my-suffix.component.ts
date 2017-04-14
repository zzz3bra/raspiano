import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ControllerActonMySuffix } from './controller-acton-my-suffix.model';
import { ControllerActonMySuffixService } from './controller-acton-my-suffix.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-controller-acton-my-suffix',
    templateUrl: './controller-acton-my-suffix.component.html'
})
export class ControllerActonMySuffixComponent implements OnInit, OnDestroy {
controllerActons: ControllerActonMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private controllerActonService: ControllerActonMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['controllerActon']);
    }

    loadAll() {
        this.controllerActonService.query().subscribe(
            (res: Response) => {
                this.controllerActons = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInControllerActons();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ControllerActonMySuffix) {
        return item.id;
    }
    registerChangeInControllerActons() {
        this.eventSubscriber = this.eventManager.subscribe('controllerActonListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
