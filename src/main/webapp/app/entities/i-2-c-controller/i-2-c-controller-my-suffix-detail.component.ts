import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { I2cControllerMySuffix } from './i-2-c-controller-my-suffix.model';
import { I2cControllerMySuffixService } from './i-2-c-controller-my-suffix.service';

@Component({
    selector: 'jhi-i-2-c-controller-my-suffix-detail',
    templateUrl: './i-2-c-controller-my-suffix-detail.component.html'
})
export class I2cControllerMySuffixDetailComponent implements OnInit, OnDestroy {

    i2cController: I2cControllerMySuffix;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private i2cControllerService: I2cControllerMySuffixService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['i2cController', 'controllerType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInI2cControllers();
    }

    load(id) {
        this.i2cControllerService.find(id).subscribe((i2cController) => {
            this.i2cController = i2cController;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInI2cControllers() {
        this.eventSubscriber = this.eventManager.subscribe('i2cControllerListModification', (response) => this.load(this.i2cController.id));
    }
}
