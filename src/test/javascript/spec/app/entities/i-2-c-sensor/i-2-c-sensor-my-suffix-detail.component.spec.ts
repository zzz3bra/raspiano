import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RaspianoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { I2cSensorMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/i-2-c-sensor/i-2-c-sensor-my-suffix-detail.component';
import { I2cSensorMySuffixService } from '../../../../../../main/webapp/app/entities/i-2-c-sensor/i-2-c-sensor-my-suffix.service';
import { I2cSensorMySuffix } from '../../../../../../main/webapp/app/entities/i-2-c-sensor/i-2-c-sensor-my-suffix.model';

describe('Component Tests', () => {

    describe('I2cSensorMySuffix Management Detail Component', () => {
        let comp: I2cSensorMySuffixDetailComponent;
        let fixture: ComponentFixture<I2cSensorMySuffixDetailComponent>;
        let service: I2cSensorMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RaspianoTestModule],
                declarations: [I2cSensorMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    I2cSensorMySuffixService,
                    EventManager
                ]
            }).overrideComponent(I2cSensorMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(I2cSensorMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(I2cSensorMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new I2cSensorMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.i2cSensor).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
