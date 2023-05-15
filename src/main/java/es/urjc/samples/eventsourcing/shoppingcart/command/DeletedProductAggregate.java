package es.urjc.samples.eventsourcing.shoppingcart.command;

import es.urjc.samples.eventsourcing.shoppingcart.coreapi.command.CreateDeletedProductCommand;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.CreatedDeletedProductEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class DeletedProductAggregate {
    @AggregateIdentifier
    private String deletedProductId;

    @CommandHandler
    public DeletedProductAggregate(CreateDeletedProductCommand command) {
        AggregateLifecycle.apply(new CreatedDeletedProductEvent(command.getDeletedProductId(), command.getCartId(), command.getProductId(),
                command.getQuantity()));
    }

    @EventSourcingHandler
    public void handle(CreatedDeletedProductEvent event) {
        this.deletedProductId = event.getDeletedProductId();
    }
}
