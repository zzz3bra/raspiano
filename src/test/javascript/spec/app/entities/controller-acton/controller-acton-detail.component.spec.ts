import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RaspianoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ControllerActonDetailComponent } from '../../../../../../main/webapp/app/entities/controller-acton/controller-acton-detail.component';
import { ControllerActonService } from '../../../../../../main/webapp/app/entities/controller-acton/controller-acton.service';
import { ControllerActon } from '../../../../../../main/webapp/app/entities/controller-acton/controller-acton.model';

describe('Component Tests', () => {

    describe('ControllerActon Management Detail Component', () => {
        let comp: ControllerActonDetailComponent;
        let fixture: ComponentFixture<ControllerActonDetailComponent>;
        let service: ControllerActonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RaspianoTestModule],
                declarations: [ControllerActonDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ControllerActonService,
                    EventManager
                ]
            }).overrideComponent(ControllerActonDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ControllerActonDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ControllerActonService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ControllerActon(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.controllerActon).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
