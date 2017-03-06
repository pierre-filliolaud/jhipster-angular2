package net.filecode.angular2.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.filecode.angular2.domain.Trade;
import net.filecode.angular2.service.TradeService;
import net.filecode.angular2.web.rest.util.HeaderUtil;
import net.filecode.angular2.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Trade.
 */
@RestController
@RequestMapping("/api")
public class TradeResource {

    private final Logger log = LoggerFactory.getLogger(TradeResource.class);

    private static final String ENTITY_NAME = "trade";
        
    private final TradeService tradeService;

    public TradeResource(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * POST  /trades : Create a new trade.
     *
     * @param trade the trade to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trade, or with status 400 (Bad Request) if the trade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trades")
    @Timed
    public ResponseEntity<Trade> createTrade(@RequestBody Trade trade) throws URISyntaxException {
        log.debug("REST request to save Trade : {}", trade);
        if (trade.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new trade cannot already have an ID")).body(null);
        }
        Trade result = tradeService.save(trade);
        return ResponseEntity.created(new URI("/api/trades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trades : Updates an existing trade.
     *
     * @param trade the trade to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trade,
     * or with status 400 (Bad Request) if the trade is not valid,
     * or with status 500 (Internal Server Error) if the trade couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trades")
    @Timed
    public ResponseEntity<Trade> updateTrade(@RequestBody Trade trade) throws URISyntaxException {
        log.debug("REST request to update Trade : {}", trade);
        if (trade.getId() == null) {
            return createTrade(trade);
        }
        Trade result = tradeService.save(trade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trades : get all the trades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trades in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/trades")
    @Timed
    public ResponseEntity<List<Trade>> getAllTrades(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Trades");
        Page<Trade> page = tradeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trades/:id : get the "id" trade.
     *
     * @param id the id of the trade to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trade, or with status 404 (Not Found)
     */
    @GetMapping("/trades/{id}")
    @Timed
    public ResponseEntity<Trade> getTrade(@PathVariable String id) {
        log.debug("REST request to get Trade : {}", id);
        Trade trade = tradeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(trade));
    }

    /**
     * DELETE  /trades/:id : delete the "id" trade.
     *
     * @param id the id of the trade to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trades/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrade(@PathVariable String id) {
        log.debug("REST request to delete Trade : {}", id);
        tradeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
