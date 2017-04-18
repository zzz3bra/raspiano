import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { I2cSensor } from './i-2-c-sensor.model';
import { I2cSensorService } from './i-2-c-sensor.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-i-2-c-sensor',
    templateUrl: './i-2-c-sensor.component.html'
})
export class I2cSensorComponent implements OnInit, OnDestroy {
i2cSensors: I2cSensor[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private i2cSensorService: I2cSensorService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['i2cSensor', 'sensorType']);
    }

    loadAll() {
        this.i2cSensorService.query().subscribe(
            (res: Response) => {
                this.i2cSensors = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInI2cSensors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: I2cSensor) {
        return item.id;
    }
    registerChangeInI2cSensors() {
        this.eventSubscriber = this.eventManager.subscribe('i2cSensorListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
