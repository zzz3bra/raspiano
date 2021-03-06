import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { I2cDevice } from './i-2-c-device.model';
import { I2cDeviceService } from './i-2-c-device.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-i-2-c-device',
    templateUrl: './i-2-c-device.component.html'
})
export class I2cDeviceComponent implements OnInit, OnDestroy {
i2cDevices: I2cDevice[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private i2cDeviceService: I2cDeviceService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['i2cDevice', 'deviceType']);
    }

    loadAll() {
        this.i2cDeviceService.query().subscribe(
            (res: Response) => {
                this.i2cDevices = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInI2cDevices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: I2cDevice) {
        return item.id;
    }
    registerChangeInI2cDevices() {
        this.eventSubscriber = this.eventManager.subscribe('i2cDeviceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
