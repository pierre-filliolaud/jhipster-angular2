package net.filecode.angular2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.filecode.angular2.domain.Trade;
import net.filecode.angular2.event.CustomSpringEventPublisher;
import net.filecode.angular2.repository.TradeRepository;

/**
 * Service Implementation for managing Trade.
 */
@Service
public class TradeService {

    private final Logger log = LoggerFactory.getLogger(TradeService.class);
    
    private final TradeRepository tradeRepository;
    
    private final CustomSpringEventPublisher customSpringEventPublisher;

    public TradeService(TradeRepository tradeRepository, CustomSpringEventPublisher customSpringEventPublisher) {
        this.tradeRepository = tradeRepository;
        this.customSpringEventPublisher = customSpringEventPublisher;
    }

    /**
     * Save a trade.
     *
     * @param trade the entity to save
     * @return the persisted entity
     */
    public Trade save(Trade trade) {
        log.debug("Request to save Trade : {}", trade);
        Trade result = tradeRepository.save(trade);
        return result;
    }

    /**
     *  Get all the trades.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Trade> findAll(Pageable pageable) {
        log.debug("Request to get all Trades");
        Page<Trade> result = tradeRepository.findAll(pageable);
        customSpringEventPublisher.doStuffAndPublishAnEvent("Hello world!");
        return result;
    }

    /**
     *  Get one trade by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Trade findOne(String id) {
        log.debug("Request to get Trade : {}", id);
        Trade trade = tradeRepository.findOne(id);
        return trade;
    }

    /**
     *  Delete the  trade by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Trade : {}", id);
        tradeRepository.delete(id);
    }
}
