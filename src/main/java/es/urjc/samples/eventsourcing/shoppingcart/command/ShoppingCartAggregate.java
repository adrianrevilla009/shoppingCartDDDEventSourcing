package es.urjc.samples.eventsourcing.shoppingcart.command;

import es.urjc.samples.eventsourcing.shoppingcart.coreapi.command.AddItemCommand;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.command.CreateProductCommand;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.command.CreateShoppingCartCommand;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.command.RemoveItemCommand;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;

@Aggregate
public class ShoppingCartAggregate {
    @AggregateIdentifier
    private String cartId;
    private String customerId;
    private List<String> cartItemIdList;

    public ShoppingCartAggregate() {
    }

    @CommandHandler
    public ShoppingCartAggregate(CreateShoppingCartCommand command) {
        AggregateLifecycle.apply(new CreatedShoppingCartEvent(command.getCustomerId(), command.getCartId()));
    }

    @CommandHandler
    public void on(AddItemCommand command){
        AggregateLifecycle.apply(new AddedItemEvent(command.getCartId(), command.getProductId(), command.getQuantity()));
    }

    @CommandHandler
    public void on(RemoveItemCommand command){
        AggregateLifecycle.apply(new RemoveItemCommand(command.getCartId(), command.getProductId(), command.getQuantity()));
    }

    @EventSourcingHandler
    public void handle(CreatedShoppingCartEvent event) {
        this.customerId = event.getCustomerId();
        this.cartId = event.getCartId();
    }

    @EventSourcingHandler
    public void handle(AddedItemEvent event) {
        if (cartItemIdList == null) {
            cartItemIdList = new ArrayList<>();
        }
        this.cartId = event.getCartId();
        this.cartItemIdList.add(event.getProductId());
    }

    @EventSourcingHandler
    public void handle(RemovedItemEvent event) {
        this.cartId = event.getCartId();
        if (cartItemIdList != null && cartItemIdList.contains(event.getProductId())) {
            this.cartItemIdList.remove(event.getProductId());
        }
    }
}
