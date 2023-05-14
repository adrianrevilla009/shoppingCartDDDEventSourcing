package es.urjc.samples.eventsourcing.shoppingcart.coreapi.query;

public class DeletedProductsQuery {
    private String cartId;

    public DeletedProductsQuery(String cartId) {
        this.cartId = cartId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
}
