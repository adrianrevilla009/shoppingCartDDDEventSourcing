package es.urjc.samples.eventsourcing.shoppingcart.coreapi.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class AddItemCommand {
    @TargetAggregateIdentifier
    private String cartId;
    private String productId;
    private int quantity;

    public AddItemCommand(String cartId, String productId, int quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
