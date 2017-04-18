import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RaspianoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RaspberryDetailComponent } from '../../../../../../main/webapp/app/entities/raspberry/raspberry-detail.component';
import { RaspberryService } from '../../../../../../main/webapp/app/entities/raspberry/raspberry.service';
import { Raspberry } from '../../../../../../main/webapp/app/entities/raspberry/raspberry.model';

describe('Component Tests', () => {

    describe('Raspberry Management Detail Component', () => {
        let comp: RaspberryDetailComponent;
        let fixture: ComponentFixture<RaspberryDetailComponent>;
        let service: RaspberryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RaspianoTestModule],
                declarations: [RaspberryDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RaspberryService,
                    EventManager
                ]
            }).overrideComponent(RaspberryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RaspberryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RaspberryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Raspberry(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.raspberry).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
