import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TradeComponent } from './trade.component';
import { TradeDetailComponent } from './trade-detail.component';
import { TradePopupComponent } from './trade-dialog.component';
import { TradeDeletePopupComponent } from './trade-delete-dialog.component';

import { Principal } from '../../shared';


export const tradeRoute: Routes = [
  {
    path: 'trade',
    component: TradeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Trades'
    }
  }, {
    path: 'trade/:id',
    component: TradeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Trades'
    }
  }
];

export const tradePopupRoute: Routes = [
  {
    path: 'trade-new',
    component: TradePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Trades'
    },
    outlet: 'popup'
  },
  {
    path: 'trade/:id/edit',
    component: TradePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Trades'
    },
    outlet: 'popup'
  },
  {
    path: 'trade/:id/delete',
    component: TradeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Trades'
    },
    outlet: 'popup'
  }
];
