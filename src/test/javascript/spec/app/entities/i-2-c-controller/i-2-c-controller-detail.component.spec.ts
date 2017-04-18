import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RaspianoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { I2cControllerDetailComponent } from '../../../../../../main/webapp/app/entities/i-2-c-controller/i-2-c-controller-detail.component';
import { I2cControllerService } from '../../../../../../main/webapp/app/entities/i-2-c-controller/i-2-c-controller.service';
import { I2cController } from '../../../../../../main/webapp/app/entities/i-2-c-controller/i-2-c-controller.model';

describe('Component Tests', () => {

    describe('I2cController Management Detail Component', () => {
        let comp: I2cControllerDetailComponent;
        let fixture: ComponentFixture<I2cControllerDetailComponent>;
        let service: I2cControllerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RaspianoTestModule],
                declarations: [I2cControllerDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    I2cControllerService,
                    EventManager
                ]
            }).overrideComponent(I2cControllerDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(I2cControllerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(I2cControllerService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new I2cController(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.i2cController).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
