import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { I2cSensorMySuffix } from './i-2-c-sensor-my-suffix.model';
import { I2cSensorMySuffixService } from './i-2-c-sensor-my-suffix.service';

@Component({
    selector: 'jhi-i-2-c-sensor-my-suffix-detail',
    templateUrl: './i-2-c-sensor-my-suffix-detail.component.html'
})
export class I2cSensorMySuffixDetailComponent implements OnInit, OnDestroy {

    i2cSensor: I2cSensorMySuffix;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private i2cSensorService: I2cSensorMySuffixService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['i2cSensor', 'sensorType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInI2cSensors();
    }

    load (id) {
        this.i2cSensorService.find(id).subscribe(i2cSensor => {
            this.i2cSensor = i2cSensor;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInI2cSensors() {
        this.eventSubscriber = this.eventManager.subscribe('i2cSensorListModification', response => this.load(this.i2cSensor.id));
    }

}
