import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { ClimateMySuffix } from './climate-my-suffix.model';
import { ClimateMySuffixService } from './climate-my-suffix.service';

@Component({
    selector: 'jhi-climate-my-suffix-detail',
    templateUrl: './climate-my-suffix-detail.component.html'
})
export class ClimateMySuffixDetailComponent implements OnInit, OnDestroy {

    climate: ClimateMySuffix;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private climateService: ClimateMySuffixService,
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
