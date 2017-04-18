import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { I2cSensorComponent } from './i-2-c-sensor.component';
import { I2cSensorDetailComponent } from './i-2-c-sensor-detail.component';
import { I2cSensorPopupComponent } from './i-2-c-sensor-dialog.component';
import { I2cSensorDeletePopupComponent } from './i-2-c-sensor-delete-dialog.component';

import { Principal } from '../../shared';

export const i2cSensorRoute: Routes = [
  {
    path: 'i-2-c-sensor',
    component: I2cSensorComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cSensor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'i-2-c-sensor/:id',
    component: I2cSensorDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cSensor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const i2cSensorPopupRoute: Routes = [
  {
    path: 'i-2-c-sensor-new',
    component: I2cSensorPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cSensor.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'i-2-c-sensor/:id/edit',
    component: I2cSensorPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cSensor.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'i-2-c-sensor/:id/delete',
    component: I2cSensorDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cSensor.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
