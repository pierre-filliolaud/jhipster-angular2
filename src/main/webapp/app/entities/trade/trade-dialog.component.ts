import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Trade } from './trade.model';
import { TradePopupService } from './trade-popup.service';
import { TradeService } from './trade.service';
@Component({
    selector: 'jhi-trade-dialog',
    templateUrl: './trade-dialog.component.html'
})
export class TradeDialogComponent implements OnInit {

    trade: Trade;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private tradeService: TradeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.trade.id !== undefined) {
            this.tradeService.update(this.trade)
                .subscribe((res: Trade) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.tradeService.create(this.trade)
                .subscribe((res: Trade) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Trade) {
        this.eventManager.broadcast({ name: 'tradeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-trade-popup',
    template: ''
})
export class TradePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private tradePopupService: TradePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.tradePopupService
                    .open(TradeDialogComponent, params['id']);
            } else {
                this.modalRef = this.tradePopupService
                    .open(TradeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
