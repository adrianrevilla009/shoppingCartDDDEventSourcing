package es.urjc.samples.eventsourcing.shoppingcart.query.product;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class DeletedProductInfo {
    @Id
    private String deletedProductId;
    private String name;
    private String description;
    private BigDecimal price;

    private String productId;

    private String cartId;

    public DeletedProductInfo(String deletedProductId, String name, String description, BigDecimal price,
                              String cartId, String productId) {
        this.deletedProductId = deletedProductId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.cartId = cartId;
        this.productId = productId;
    }

    public DeletedProductInfo() {
    }

    public String getDeletedProductId() {
        return deletedProductId;
    }

    public void setDeletedProductId(String deletedProductId) {
        this.deletedProductId = deletedProductId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return "DeletedProductInfo{" +
                "deletedProductId='" + deletedProductId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", productId='" + productId + '\'' +
                ", cartId='" + cartId + '\'' +
                '}';
    }
}
