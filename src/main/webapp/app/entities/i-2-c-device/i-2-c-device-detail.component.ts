import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { I2cDevice } from './i-2-c-device.model';
import { I2cDeviceService } from './i-2-c-device.service';

@Component({
    selector: 'jhi-i-2-c-device-detail',
    templateUrl: './i-2-c-device-detail.component.html'
})
export class I2cDeviceDetailComponent implements OnInit, OnDestroy {

    i2cDevice: I2cDevice;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private i2cDeviceService: I2cDeviceService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['i2cDevice', 'deviceType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInI2cDevices();
    }

    load(id) {
        this.i2cDeviceService.find(id).subscribe((i2cDevice) => {
            this.i2cDevice = i2cDevice;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInI2cDevices() {
        this.eventSubscriber = this.eventManager.subscribe('i2cDeviceListModification', (response) => this.load(this.i2cDevice.id));
    }
}
