import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { I2cControllerComponent } from './i-2-c-controller.component';
import { I2cControllerDetailComponent } from './i-2-c-controller-detail.component';
import { I2cControllerPopupComponent } from './i-2-c-controller-dialog.component';
import { I2cControllerDeletePopupComponent } from './i-2-c-controller-delete-dialog.component';

import { Principal } from '../../shared';

export const i2cControllerRoute: Routes = [
  {
    path: 'i-2-c-controller',
    component: I2cControllerComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cController.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'i-2-c-controller/:id',
    component: I2cControllerDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cController.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const i2cControllerPopupRoute: Routes = [
  {
    path: 'i-2-c-controller-new',
    component: I2cControllerPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cController.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'i-2-c-controller/:id/edit',
    component: I2cControllerPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cController.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'i-2-c-controller/:id/delete',
    component: I2cControllerDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cController.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
