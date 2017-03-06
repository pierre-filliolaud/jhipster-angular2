package net.filecode.angular2.repository;

import net.filecode.angular2.domain.Trade;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Trade entity.
 */
@SuppressWarnings("unused")
public interface TradeRepository extends MongoRepository<Trade,String> {

}
