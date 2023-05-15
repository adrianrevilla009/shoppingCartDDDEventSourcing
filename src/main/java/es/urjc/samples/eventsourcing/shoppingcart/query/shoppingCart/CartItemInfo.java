package es.urjc.samples.eventsourcing.shoppingcart.query.shoppingCart;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CartItemInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartItemId;
    private String productId;
    private int quantity;
    // this property is stored to track deleted products of a cart
    private String shoppingCartId;

    public CartItemInfo(int cartItemId, String productId, int quantity) {
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public CartItemInfo(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public CartItemInfo(String productId, int quantity, String shoppingCartId) {
        this.productId = productId;
        this.quantity = quantity;
        this.shoppingCartId = shoppingCartId;
    }

    public CartItemInfo() {
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
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

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    @Override
    public String toString() {
        return "CartItemInfo{" +
                "cartItemId=" + cartItemId +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
