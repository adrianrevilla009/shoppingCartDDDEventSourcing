package es.urjc.samples.eventsourcing.shoppingcart.coreapi.query;

public class ProductQuery {
    private String productId;

    public ProductQuery(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
