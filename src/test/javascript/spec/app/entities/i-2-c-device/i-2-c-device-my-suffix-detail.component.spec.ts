import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RaspianoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { I2cDeviceMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/i-2-c-device/i-2-c-device-my-suffix-detail.component';
import { I2cDeviceMySuffixService } from '../../../../../../main/webapp/app/entities/i-2-c-device/i-2-c-device-my-suffix.service';
import { I2cDeviceMySuffix } from '../../../../../../main/webapp/app/entities/i-2-c-device/i-2-c-device-my-suffix.model';

describe('Component Tests', () => {

    describe('I2cDeviceMySuffix Management Detail Component', () => {
        let comp: I2cDeviceMySuffixDetailComponent;
        let fixture: ComponentFixture<I2cDeviceMySuffixDetailComponent>;
        let service: I2cDeviceMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RaspianoTestModule],
                declarations: [I2cDeviceMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    I2cDeviceMySuffixService,
                    EventManager
                ]
            }).overrideComponent(I2cDeviceMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(I2cDeviceMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(I2cDeviceMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new I2cDeviceMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.i2cDevice).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
