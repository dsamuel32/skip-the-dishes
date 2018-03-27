package br.com.skip.the.dishes.domain.customer.commons;

import br.com.skip.the.dishes.domain.customer.events.CustomerCreated;
import br.com.zup.eventsourcing.core.AggregateId;

public interface CustomerApplier {

    void on(AggregateId aggregateId, CustomerCreated customerCreated);

}
