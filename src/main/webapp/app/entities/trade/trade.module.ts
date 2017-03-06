import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';

import {
    TradeService,
    TradePopupService,
    TradeComponent,
    TradeDetailComponent,
    TradeDialogComponent,
    TradePopupComponent,
    TradeDeletePopupComponent,
    TradeDeleteDialogComponent,
    tradeRoute,
    tradePopupRoute,
} from './';

let ENTITY_STATES = [
    ...tradeRoute,
    ...tradePopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TradeComponent,
        TradeDetailComponent,
        TradeDialogComponent,
        TradeDeleteDialogComponent,
        TradePopupComponent,
        TradeDeletePopupComponent,
    ],
    entryComponents: [
        TradeComponent,
        TradeDialogComponent,
        TradePopupComponent,
        TradeDeleteDialogComponent,
        TradeDeletePopupComponent,
    ],
    providers: [
        TradeService,
        TradePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterTradeModule {}
