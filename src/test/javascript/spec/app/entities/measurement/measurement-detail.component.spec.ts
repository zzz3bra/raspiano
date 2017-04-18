import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RaspianoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MeasurementDetailComponent } from '../../../../../../main/webapp/app/entities/measurement/measurement-detail.component';
import { MeasurementService } from '../../../../../../main/webapp/app/entities/measurement/measurement.service';
import { Measurement } from '../../../../../../main/webapp/app/entities/measurement/measurement.model';

describe('Component Tests', () => {

    describe('Measurement Management Detail Component', () => {
        let comp: MeasurementDetailComponent;
        let fixture: ComponentFixture<MeasurementDetailComponent>;
        let service: MeasurementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RaspianoTestModule],
                declarations: [MeasurementDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MeasurementService,
                    EventManager
                ]
            }).overrideComponent(MeasurementDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MeasurementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MeasurementService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Measurement(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.measurement).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
