import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { RaspberryMySuffix } from './raspberry-my-suffix.model';
import { RaspberryMySuffixService } from './raspberry-my-suffix.service';

@Component({
    selector: 'jhi-raspberry-my-suffix-detail',
    templateUrl: './raspberry-my-suffix-detail.component.html'
})
export class RaspberryMySuffixDetailComponent implements OnInit, OnDestroy {

    raspberry: RaspberryMySuffix;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private raspberryService: RaspberryMySuffixService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['raspberry']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRaspberries();
    }

    load(id) {
        this.raspberryService.find(id).subscribe((raspberry) => {
            this.raspberry = raspberry;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRaspberries() {
        this.eventSubscriber = this.eventManager.subscribe('raspberryListModification', (response) => this.load(this.raspberry.id));
    }
}
