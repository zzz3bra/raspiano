import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RaspianoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { I2cControllerMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/i-2-c-controller/i-2-c-controller-my-suffix-detail.component';
import { I2cControllerMySuffixService } from '../../../../../../main/webapp/app/entities/i-2-c-controller/i-2-c-controller-my-suffix.service';
import { I2cControllerMySuffix } from '../../../../../../main/webapp/app/entities/i-2-c-controller/i-2-c-controller-my-suffix.model';

describe('Component Tests', () => {

    describe('I2cControllerMySuffix Management Detail Component', () => {
        let comp: I2cControllerMySuffixDetailComponent;
        let fixture: ComponentFixture<I2cControllerMySuffixDetailComponent>;
        let service: I2cControllerMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RaspianoTestModule],
                declarations: [I2cControllerMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    I2cControllerMySuffixService,
                    EventManager
                ]
            }).overrideComponent(I2cControllerMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(I2cControllerMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(I2cControllerMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new I2cControllerMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.i2cController).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
