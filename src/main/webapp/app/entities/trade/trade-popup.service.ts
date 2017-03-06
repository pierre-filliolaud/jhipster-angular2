import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Trade } from './trade.model';
import { TradeService } from './trade.service';
@Injectable()
export class TradePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private tradeService: TradeService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.tradeService.find(id).subscribe(trade => {
                if (trade.creationDate) {
                    trade.creationDate = {
                        year: trade.creationDate.getFullYear(),
                        month: trade.creationDate.getMonth() + 1,
                        day: trade.creationDate.getDate()
                    };
                }
                this.tradeModalRef(component, trade);
            });
        } else {
            return this.tradeModalRef(component, new Trade());
        }
    }

    tradeModalRef(component: Component, trade: Trade): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.trade = trade;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
