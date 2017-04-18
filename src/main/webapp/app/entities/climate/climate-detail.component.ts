import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Climate } from './climate.model';
import { ClimateService } from './climate.service';

@Component({
    selector: 'jhi-climate-detail',
    templateUrl: './climate-detail.component.html'
})
export class ClimateDetailComponent implements OnInit, OnDestroy {

    climate: Climate;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private climateService: ClimateService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['climate']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClimates();
    }

    load(id) {
        this.climateService.find(id).subscribe((climate) => {
            this.climate = climate;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClimates() {
        this.eventSubscriber = this.eventManager.subscribe('climateListModification', (response) => this.load(this.climate.id));
    }
}
