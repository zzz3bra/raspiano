import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ControllerActonComponent } from './controller-acton.component';
import { ControllerActonDetailComponent } from './controller-acton-detail.component';
import { ControllerActonPopupComponent } from './controller-acton-dialog.component';
import { ControllerActonDeletePopupComponent } from './controller-acton-delete-dialog.component';

import { Principal } from '../../shared';

export const controllerActonRoute: Routes = [
  {
    path: 'controller-acton',
    component: ControllerActonComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.controllerActon.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'controller-acton/:id',
    component: ControllerActonDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.controllerActon.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const controllerActonPopupRoute: Routes = [
  {
    path: 'controller-acton-new',
    component: ControllerActonPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.controllerActon.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'controller-acton/:id/edit',
    component: ControllerActonPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.controllerActon.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'controller-acton/:id/delete',
    component: ControllerActonDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.controllerActon.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
