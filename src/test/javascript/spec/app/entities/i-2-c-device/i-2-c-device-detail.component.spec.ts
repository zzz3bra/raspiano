import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RaspianoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { I2cDeviceDetailComponent } from '../../../../../../main/webapp/app/entities/i-2-c-device/i-2-c-device-detail.component';
import { I2cDeviceService } from '../../../../../../main/webapp/app/entities/i-2-c-device/i-2-c-device.service';
import { I2cDevice } from '../../../../../../main/webapp/app/entities/i-2-c-device/i-2-c-device.model';

describe('Component Tests', () => {

    describe('I2cDevice Management Detail Component', () => {
        let comp: I2cDeviceDetailComponent;
        let fixture: ComponentFixture<I2cDeviceDetailComponent>;
        let service: I2cDeviceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RaspianoTestModule],
                declarations: [I2cDeviceDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    I2cDeviceService,
                    EventManager
                ]
            }).overrideComponent(I2cDeviceDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(I2cDeviceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(I2cDeviceService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new I2cDevice(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.i2cDevice).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
