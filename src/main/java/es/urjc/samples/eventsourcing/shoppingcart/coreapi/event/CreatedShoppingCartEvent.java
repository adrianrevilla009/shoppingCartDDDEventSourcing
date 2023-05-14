package es.urjc.samples.eventsourcing.shoppingcart.coreapi.event;

public class CreatedShoppingCartEvent {
    private String customerId;
    private String cartId;

    public CreatedShoppingCartEvent(String customerId, String cartId) {
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
