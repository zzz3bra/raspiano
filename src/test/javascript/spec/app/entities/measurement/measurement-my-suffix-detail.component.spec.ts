import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RaspianoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MeasurementMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/measurement/measurement-my-suffix-detail.component';
import { MeasurementMySuffixService } from '../../../../../../main/webapp/app/entities/measurement/measurement-my-suffix.service';
import { MeasurementMySuffix } from '../../../../../../main/webapp/app/entities/measurement/measurement-my-suffix.model';

describe('Component Tests', () => {

    describe('MeasurementMySuffix Management Detail Component', () => {
        let comp: MeasurementMySuffixDetailComponent;
        let fixture: ComponentFixture<MeasurementMySuffixDetailComponent>;
        let service: MeasurementMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RaspianoTestModule],
                declarations: [MeasurementMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MeasurementMySuffixService,
                    EventManager
                ]
            }).overrideComponent(MeasurementMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MeasurementMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MeasurementMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MeasurementMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.measurement).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
