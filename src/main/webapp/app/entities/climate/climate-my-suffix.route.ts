import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClimateMySuffixComponent } from './climate-my-suffix.component';
import { ClimateMySuffixDetailComponent } from './climate-my-suffix-detail.component';
import { ClimateMySuffixPopupComponent } from './climate-my-suffix-dialog.component';
import { ClimateMySuffixDeletePopupComponent } from './climate-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const climateRoute: Routes = [
  {
    path: 'climate-my-suffix',
    component: ClimateMySuffixComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.climate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'climate-my-suffix/:id',
    component: ClimateMySuffixDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.climate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const climatePopupRoute: Routes = [
  {
    path: 'climate-my-suffix-new',
    component: ClimateMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.climate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'climate-my-suffix/:id/edit',
    component: ClimateMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.climate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'climate-my-suffix/:id/delete',
    component: ClimateMySuffixDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.climate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
