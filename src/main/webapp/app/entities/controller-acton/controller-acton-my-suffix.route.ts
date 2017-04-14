import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ControllerActonMySuffixComponent } from './controller-acton-my-suffix.component';
import { ControllerActonMySuffixDetailComponent } from './controller-acton-my-suffix-detail.component';
import { ControllerActonMySuffixPopupComponent } from './controller-acton-my-suffix-dialog.component';
import { ControllerActonMySuffixDeletePopupComponent } from './controller-acton-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';


export const controllerActonRoute: Routes = [
  {
    path: 'controller-acton-my-suffix',
    component: ControllerActonMySuffixComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.controllerActon.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'controller-acton-my-suffix/:id',
    component: ControllerActonMySuffixDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.controllerActon.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const controllerActonPopupRoute: Routes = [
  {
    path: 'controller-acton-my-suffix-new',
    component: ControllerActonMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.controllerActon.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'controller-acton-my-suffix/:id/edit',
    component: ControllerActonMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.controllerActon.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'controller-acton-my-suffix/:id/delete',
    component: ControllerActonMySuffixDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.controllerActon.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
