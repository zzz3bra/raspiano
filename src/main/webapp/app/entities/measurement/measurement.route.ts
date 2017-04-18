import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MeasurementComponent } from './measurement.component';
import { MeasurementDetailComponent } from './measurement-detail.component';
import { MeasurementPopupComponent } from './measurement-dialog.component';
import { MeasurementDeletePopupComponent } from './measurement-delete-dialog.component';

import { Principal } from '../../shared';

export const measurementRoute: Routes = [
  {
    path: 'measurement',
    component: MeasurementComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.measurement.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'measurement/:id',
    component: MeasurementDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.measurement.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const measurementPopupRoute: Routes = [
  {
    path: 'measurement-new',
    component: MeasurementPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.measurement.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'measurement/:id/edit',
    component: MeasurementPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.measurement.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'measurement/:id/delete',
    component: MeasurementDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.measurement.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
