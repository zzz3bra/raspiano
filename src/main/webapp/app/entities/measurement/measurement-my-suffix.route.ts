import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MeasurementMySuffixComponent } from './measurement-my-suffix.component';
import { MeasurementMySuffixDetailComponent } from './measurement-my-suffix-detail.component';
import { MeasurementMySuffixPopupComponent } from './measurement-my-suffix-dialog.component';
import { MeasurementMySuffixDeletePopupComponent } from './measurement-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const measurementRoute: Routes = [
  {
    path: 'measurement-my-suffix',
    component: MeasurementMySuffixComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.measurement.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'measurement-my-suffix/:id',
    component: MeasurementMySuffixDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.measurement.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const measurementPopupRoute: Routes = [
  {
    path: 'measurement-my-suffix-new',
    component: MeasurementMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.measurement.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'measurement-my-suffix/:id/edit',
    component: MeasurementMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.measurement.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'measurement-my-suffix/:id/delete',
    component: MeasurementMySuffixDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.measurement.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
