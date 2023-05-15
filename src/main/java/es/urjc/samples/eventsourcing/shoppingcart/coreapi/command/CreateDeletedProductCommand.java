package es.urjc.samples.eventsourcing.shoppingcart.coreapi.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
public class CreateDeletedProductCommand {
    @TargetAggregateIdentifier
    private String deletedProductId;
    private String cartId;
    private String productId;
    private int quantity;

    public CreateDeletedProductCommand(String deletedProductId, String cartId, String productId, int quantity) {
        this.deletedProductId = deletedProductId;
        this.cartId = cartId;
        this.deletedProductId = productId;
        this.quantity = quantity;
    }

    public CreateDeletedProductCommand() {
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getDeletedProductId() {
        return deletedProductId;
    }

    public void setDeletedProductId(String deletedProductId) {
        this.deletedProductId = deletedProductId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
