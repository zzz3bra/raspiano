import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RaspianoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClimateDetailComponent } from '../../../../../../main/webapp/app/entities/climate/climate-detail.component';
import { ClimateService } from '../../../../../../main/webapp/app/entities/climate/climate.service';
import { Climate } from '../../../../../../main/webapp/app/entities/climate/climate.model';

describe('Component Tests', () => {

    describe('Climate Management Detail Component', () => {
        let comp: ClimateDetailComponent;
        let fixture: ComponentFixture<ClimateDetailComponent>;
        let service: ClimateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RaspianoTestModule],
                declarations: [ClimateDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClimateService,
                    EventManager
                ]
            }).overrideComponent(ClimateDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClimateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClimateService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Climate(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.climate).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
