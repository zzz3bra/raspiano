import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RaspberryMySuffixComponent } from './raspberry-my-suffix.component';
import { RaspberryMySuffixDetailComponent } from './raspberry-my-suffix-detail.component';
import { RaspberryMySuffixPopupComponent } from './raspberry-my-suffix-dialog.component';
import { RaspberryMySuffixDeletePopupComponent } from './raspberry-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';


export const raspberryRoute: Routes = [
  {
    path: 'raspberry-my-suffix',
    component: RaspberryMySuffixComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.raspberry.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'raspberry-my-suffix/:id',
    component: RaspberryMySuffixDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.raspberry.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const raspberryPopupRoute: Routes = [
  {
    path: 'raspberry-my-suffix-new',
    component: RaspberryMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.raspberry.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'raspberry-my-suffix/:id/edit',
    component: RaspberryMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.raspberry.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'raspberry-my-suffix/:id/delete',
    component: RaspberryMySuffixDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'raspianoApp.raspberry.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
