import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { I2cControllerMySuffixComponent } from './i-2-c-controller-my-suffix.component';
import { I2cControllerMySuffixDetailComponent } from './i-2-c-controller-my-suffix-detail.component';
import { I2cControllerMySuffixPopupComponent } from './i-2-c-controller-my-suffix-dialog.component';
import { I2cControllerMySuffixDeletePopupComponent } from './i-2-c-controller-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';


export const i2cControllerRoute: Routes = [
  {
    path: 'i-2-c-controller-my-suffix',
    component: I2cControllerMySuffixComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cController.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'i-2-c-controller-my-suffix/:id',
    component: I2cControllerMySuffixDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cController.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const i2cControllerPopupRoute: Routes = [
  {
    path: 'i-2-c-controller-my-suffix-new',
    component: I2cControllerMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cController.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'i-2-c-controller-my-suffix/:id/edit',
    component: I2cControllerMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cController.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'i-2-c-controller-my-suffix/:id/delete',
    component: I2cControllerMySuffixDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.i2cController.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
