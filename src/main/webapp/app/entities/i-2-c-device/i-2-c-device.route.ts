import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { I2cDeviceComponent } from './i-2-c-device.component';
import { I2cDeviceDetailComponent } from './i-2-c-device-detail.component';
import { I2cDevicePopupComponent } from './i-2-c-device-dialog.component';
import { I2cDeviceDeletePopupComponent } from './i-2-c-device-delete-dialog.component';

import { Principal } from '../../shared';

export const i2cDeviceRoute: Routes = [
  {
    path: 'i-2-c-device',
    component: I2cDeviceComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cDevice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'i-2-c-device/:id',
    component: I2cDeviceDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cDevice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const i2cDevicePopupRoute: Routes = [
  {
    path: 'i-2-c-device-new',
    component: I2cDevicePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cDevice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'i-2-c-device/:id/edit',
    component: I2cDevicePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cDevice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'i-2-c-device/:id/delete',
    component: I2cDeviceDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cDevice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
