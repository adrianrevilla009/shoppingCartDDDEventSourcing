package es.urjc.samples.eventsourcing.shoppingcart.coreapi.query;

public class ShoppingCartQuery {
    private String cartId;

    public ShoppingCartQuery(String cartId) {
        this.cartId = cartId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
}
