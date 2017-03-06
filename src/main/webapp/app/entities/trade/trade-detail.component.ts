import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Trade } from './trade.model';
import { TradeService } from './trade.service';

@Component({
    selector: 'jhi-trade-detail',
    templateUrl: './trade-detail.component.html'
})
export class TradeDetailComponent implements OnInit, OnDestroy {

    trade: Trade;
    private subscription: any;

    constructor(
        private tradeService: TradeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.tradeService.find(id).subscribe(trade => {
            this.trade = trade;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
