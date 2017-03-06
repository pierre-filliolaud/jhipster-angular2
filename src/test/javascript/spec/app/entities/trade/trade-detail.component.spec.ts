import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TradeDetailComponent } from '../../../../../../main/webapp/app/entities/trade/trade-detail.component';
import { TradeService } from '../../../../../../main/webapp/app/entities/trade/trade.service';
import { Trade } from '../../../../../../main/webapp/app/entities/trade/trade.model';

describe('Component Tests', () => {

    describe('Trade Management Detail Component', () => {
        let comp: TradeDetailComponent;
        let fixture: ComponentFixture<TradeDetailComponent>;
        let service: TradeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [TradeDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    TradeService
                ]
            }).overrideComponent(TradeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TradeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TradeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Trade('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.trade).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
