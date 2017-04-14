import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RaspianoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RaspberryMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/raspberry/raspberry-my-suffix-detail.component';
import { RaspberryMySuffixService } from '../../../../../../main/webapp/app/entities/raspberry/raspberry-my-suffix.service';
import { RaspberryMySuffix } from '../../../../../../main/webapp/app/entities/raspberry/raspberry-my-suffix.model';

describe('Component Tests', () => {

    describe('RaspberryMySuffix Management Detail Component', () => {
        let comp: RaspberryMySuffixDetailComponent;
        let fixture: ComponentFixture<RaspberryMySuffixDetailComponent>;
        let service: RaspberryMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RaspianoTestModule],
                declarations: [RaspberryMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RaspberryMySuffixService,
                    EventManager
                ]
            }).overrideComponent(RaspberryMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RaspberryMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RaspberryMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RaspberryMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.raspberry).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
