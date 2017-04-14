import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { RaspberryMySuffix } from './raspberry-my-suffix.model';
import { RaspberryMySuffixService } from './raspberry-my-suffix.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-raspberry-my-suffix',
    templateUrl: './raspberry-my-suffix.component.html'
})
export class RaspberryMySuffixComponent implements OnInit, OnDestroy {
raspberries: RaspberryMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private raspberryService: RaspberryMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['raspberry']);
    }

    loadAll() {
        this.raspberryService.query().subscribe(
            (res: Response) => {
                this.raspberries = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRaspberries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: RaspberryMySuffix) {
        return item.id;
    }



    registerChangeInRaspberries() {
        this.eventSubscriber = this.eventManager.subscribe('raspberryListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
