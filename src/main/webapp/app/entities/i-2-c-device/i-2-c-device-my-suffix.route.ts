import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { I2cDeviceMySuffixComponent } from './i-2-c-device-my-suffix.component';
import { I2cDeviceMySuffixDetailComponent } from './i-2-c-device-my-suffix-detail.component';
import { I2cDeviceMySuffixPopupComponent } from './i-2-c-device-my-suffix-dialog.component';
import { I2cDeviceMySuffixDeletePopupComponent } from './i-2-c-device-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';


export const i2cDeviceRoute: Routes = [
  {
    path: 'i-2-c-device-my-suffix',
    component: I2cDeviceMySuffixComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cDevice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'i-2-c-device-my-suffix/:id',
    component: I2cDeviceMySuffixDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cDevice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const i2cDevicePopupRoute: Routes = [
  {
    path: 'i-2-c-device-my-suffix-new',
    component: I2cDeviceMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cDevice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'i-2-c-device-my-suffix/:id/edit',
    component: I2cDeviceMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cDevice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'i-2-c-device-my-suffix/:id/delete',
    component: I2cDeviceMySuffixDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cDevice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
