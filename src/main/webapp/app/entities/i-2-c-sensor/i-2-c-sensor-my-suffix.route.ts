import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { I2cSensorMySuffixComponent } from './i-2-c-sensor-my-suffix.component';
import { I2cSensorMySuffixDetailComponent } from './i-2-c-sensor-my-suffix-detail.component';
import { I2cSensorMySuffixPopupComponent } from './i-2-c-sensor-my-suffix-dialog.component';
import { I2cSensorMySuffixDeletePopupComponent } from './i-2-c-sensor-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const i2cSensorRoute: Routes = [
  {
    path: 'i-2-c-sensor-my-suffix',
    component: I2cSensorMySuffixComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cSensor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'i-2-c-sensor-my-suffix/:id',
    component: I2cSensorMySuffixDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cSensor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const i2cSensorPopupRoute: Routes = [
  {
    path: 'i-2-c-sensor-my-suffix-new',
    component: I2cSensorMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cSensor.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'i-2-c-sensor-my-suffix/:id/edit',
    component: I2cSensorMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cSensor.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'i-2-c-sensor-my-suffix/:id/delete',
    component: I2cSensorMySuffixDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cSensor.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
