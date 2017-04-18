import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { I2cController } from './i-2-c-controller.model';
import { I2cControllerService } from './i-2-c-controller.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-i-2-c-controller',
    templateUrl: './i-2-c-controller.component.html'
})
export class I2cControllerComponent implements OnInit, OnDestroy {
i2cControllers: I2cController[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private i2cControllerService: I2cControllerService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['i2cController', 'controllerType']);
    }

    loadAll() {
        this.i2cControllerService.query().subscribe(
            (res: Response) => {
                this.i2cControllers = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInI2cControllers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: I2cController) {
        return item.id;
    }
    registerChangeInI2cControllers() {
        this.eventSubscriber = this.eventManager.subscribe('i2cControllerListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
