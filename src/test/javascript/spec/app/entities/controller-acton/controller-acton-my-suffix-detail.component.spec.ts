import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RaspianoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ControllerActonMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/controller-acton/controller-acton-my-suffix-detail.component';
import { ControllerActonMySuffixService } from '../../../../../../main/webapp/app/entities/controller-acton/controller-acton-my-suffix.service';
import { ControllerActonMySuffix } from '../../../../../../main/webapp/app/entities/controller-acton/controller-acton-my-suffix.model';

describe('Component Tests', () => {

    describe('ControllerActonMySuffix Management Detail Component', () => {
        let comp: ControllerActonMySuffixDetailComponent;
        let fixture: ComponentFixture<ControllerActonMySuffixDetailComponent>;
        let service: ControllerActonMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RaspianoTestModule],
                declarations: [ControllerActonMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ControllerActonMySuffixService,
                    EventManager
                ]
            }).overrideComponent(ControllerActonMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ControllerActonMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ControllerActonMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ControllerActonMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.controllerActon).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
