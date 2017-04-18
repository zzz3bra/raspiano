import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RaspberryComponent } from './raspberry.component';
import { RaspberryDetailComponent } from './raspberry-detail.component';
import { RaspberryPopupComponent } from './raspberry-dialog.component';
import { RaspberryDeletePopupComponent } from './raspberry-delete-dialog.component';

import { Principal } from '../../shared';

export const raspberryRoute: Routes = [
  {
    path: 'raspberry',
    component: RaspberryComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.raspberry.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'raspberry/:id',
    component: RaspberryDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.raspberry.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const raspberryPopupRoute: Routes = [
  {
    path: 'raspberry-new',
    component: RaspberryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.raspberry.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'raspberry/:id/edit',
    component: RaspberryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.raspberry.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'raspberry/:id/delete',
    component: RaspberryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.raspberry.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
