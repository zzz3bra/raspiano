import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClimateComponent } from './climate.component';
import { ClimateDetailComponent } from './climate-detail.component';
import { ClimatePopupComponent } from './climate-dialog.component';
import { ClimateDeletePopupComponent } from './climate-delete-dialog.component';

import { Principal } from '../../shared';

export const climateRoute: Routes = [
  {
    path: 'climate',
    component: ClimateComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.climate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'climate/:id',
    component: ClimateDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.climate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const climatePopupRoute: Routes = [
  {
    path: 'climate-new',
    component: ClimatePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.climate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'climate/:id/edit',
    component: ClimatePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.climate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'climate/:id/delete',
    component: ClimateDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.climate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
