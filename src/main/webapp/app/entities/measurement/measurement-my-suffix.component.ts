import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { MeasurementMySuffix } from './measurement-my-suffix.model';
import { MeasurementMySuffixService } from './measurement-my-suffix.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-measurement-my-suffix',
    templateUrl: './measurement-my-suffix.component.html'
})
export class MeasurementMySuffixComponent implements OnInit, OnDestroy {
measurements: MeasurementMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private measurementService: MeasurementMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['measurement']);
    }

    loadAll() {
        this.measurementService.query().subscribe(
            (res: Response) => {
                this.measurements = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMeasurements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: MeasurementMySuffix) {
        return item.id;
    }



    registerChangeInMeasurements() {
        this.eventSubscriber = this.eventManager.subscribe('measurementListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
