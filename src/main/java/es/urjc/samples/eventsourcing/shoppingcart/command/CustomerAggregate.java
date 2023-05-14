package es.urjc.samples.eventsourcing.shoppingcart.command;

import es.urjc.samples.eventsourcing.shoppingcart.coreapi.command.CreateCustomerCommand;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.command.CreateShoppingCartCommand;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.CreatedCustomerEvent;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.CreatedShoppingCartEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class CustomerAggregate {
    @AggregateIdentifier
    private String customerId;

    private String cartId;


    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand command) {
        AggregateLifecycle.apply(new CreatedCustomerEvent(command.getCustomerId(), command.getFullName(), command.getAddress()));
    }

    @EventSourcingHandler
    public void handle(CreatedCustomerEvent event) {
        this.customerId = event.getCustomerId();
    }
}
