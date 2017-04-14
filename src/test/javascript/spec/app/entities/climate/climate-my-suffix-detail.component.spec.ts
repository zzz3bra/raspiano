import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RaspianoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClimateMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/climate/climate-my-suffix-detail.component';
import { ClimateMySuffixService } from '../../../../../../main/webapp/app/entities/climate/climate-my-suffix.service';
import { ClimateMySuffix } from '../../../../../../main/webapp/app/entities/climate/climate-my-suffix.model';

describe('Component Tests', () => {

    describe('ClimateMySuffix Management Detail Component', () => {
        let comp: ClimateMySuffixDetailComponent;
        let fixture: ComponentFixture<ClimateMySuffixDetailComponent>;
        let service: ClimateMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RaspianoTestModule],
                declarations: [ClimateMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClimateMySuffixService,
                    EventManager
                ]
            }).overrideComponent(ClimateMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClimateMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClimateMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ClimateMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.climate).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
