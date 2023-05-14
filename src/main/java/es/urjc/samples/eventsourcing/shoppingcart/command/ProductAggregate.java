package es.urjc.samples.eventsourcing.shoppingcart.command;

import es.urjc.samples.eventsourcing.shoppingcart.coreapi.command.CreateProductCommand;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.CreatedProductEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class ProductAggregate {
    @AggregateIdentifier
    private String productId;

    @CommandHandler
    public ProductAggregate(CreateProductCommand command) {
        AggregateLifecycle.apply(new CreatedProductEvent(command.getProductId(), command.getName(),
                command.getDescription(), command.getPrice()));
    }

    @EventSourcingHandler
    public void handle(CreatedProductEvent event) {
        this.productId = event.getProductId();
    }
}
