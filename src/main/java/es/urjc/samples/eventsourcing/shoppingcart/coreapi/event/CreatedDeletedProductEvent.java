package es.urjc.samples.eventsourcing.shoppingcart.coreapi.event;

public class CreatedDeletedProductEvent {
    private String deletedProductId;
    private String cartId;
    private String productId;
    private int quantity;

    public CreatedDeletedProductEvent(String deletedProductId, String cartId, String productId, int quantity) {
        this.deletedProductId = deletedProductId;
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public CreatedDeletedProductEvent() {
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
