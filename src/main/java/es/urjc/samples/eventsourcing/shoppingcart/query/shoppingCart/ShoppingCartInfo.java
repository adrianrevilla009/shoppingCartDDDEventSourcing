package es.urjc.samples.eventsourcing.shoppingcart.query.shoppingCart;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ShoppingCartInfo {
    @Id
    private String cartId;
    private String customerId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private List<CartItemInfo> items = new ArrayList<>();

    public ShoppingCartInfo(String cartId, String customerId, List<CartItemInfo> items) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.items = items;
    }

    public ShoppingCartInfo() {
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

    public List<CartItemInfo> getItems() {
        return items;
    }

    public void setItems(List<CartItemInfo> items) {
        this.items = items;
    }

    public void addItem(CartItemInfo item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        return "ShoppingCartInfo{" +
                "cartId='" + cartId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", items=" + items +
                '}';
    }
}
