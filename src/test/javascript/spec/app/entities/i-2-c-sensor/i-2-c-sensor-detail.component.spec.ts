import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RaspianoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { I2cSensorDetailComponent } from '../../../../../../main/webapp/app/entities/i-2-c-sensor/i-2-c-sensor-detail.component';
import { I2cSensorService } from '../../../../../../main/webapp/app/entities/i-2-c-sensor/i-2-c-sensor.service';
import { I2cSensor } from '../../../../../../main/webapp/app/entities/i-2-c-sensor/i-2-c-sensor.model';

describe('Component Tests', () => {

    describe('I2cSensor Management Detail Component', () => {
        let comp: I2cSensorDetailComponent;
        let fixture: ComponentFixture<I2cSensorDetailComponent>;
        let service: I2cSensorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RaspianoTestModule],
                declarations: [I2cSensorDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    I2cSensorService,
                    EventManager
                ]
            }).overrideComponent(I2cSensorDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(I2cSensorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(I2cSensorService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new I2cSensor(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.i2cSensor).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
