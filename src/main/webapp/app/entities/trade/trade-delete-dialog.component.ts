import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Trade } from './trade.model';
import { TradePopupService } from './trade-popup.service';
import { TradeService } from './trade.service';

@Component({
    selector: 'jhi-trade-delete-dialog',
    templateUrl: './trade-delete-dialog.component.html'
})
export class TradeDeleteDialogComponent {

    trade: Trade;

    constructor(
        private tradeService: TradeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.tradeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tradeListModification',
                content: 'Deleted an trade'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-trade-delete-popup',
    template: ''
})
export class TradeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private tradePopupService: TradePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.tradePopupService
                .open(TradeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
