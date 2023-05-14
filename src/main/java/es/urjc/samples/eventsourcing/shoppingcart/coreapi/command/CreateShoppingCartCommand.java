package es.urjc.samples.eventsourcing.shoppingcart.coreapi.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateShoppingCartCommand {
    @TargetAggregateIdentifier
    private String customerId;
    private String cartId;

    public CreateShoppingCartCommand(String customerId, String cartId) {
        this.customerId = customerId;
        this.cartId = cartId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
