import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { MeasurementMySuffix } from './measurement-my-suffix.model';
import { MeasurementMySuffixService } from './measurement-my-suffix.service';

@Component({
    selector: 'jhi-measurement-my-suffix-detail',
    templateUrl: './measurement-my-suffix-detail.component.html'
})
export class MeasurementMySuffixDetailComponent implements OnInit, OnDestroy {

    measurement: MeasurementMySuffix;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private measurementService: MeasurementMySuffixService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['measurement']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMeasurements();
    }

    load(id) {
        this.measurementService.find(id).subscribe((measurement) => {
            this.measurement = measurement;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMeasurements() {
        this.eventSubscriber = this.eventManager.subscribe('measurementListModification', (response) => this.load(this.measurement.id));
    }
}
