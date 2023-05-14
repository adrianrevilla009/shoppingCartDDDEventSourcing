package es.urjc.samples.eventsourcing.shoppingcart.coreapi.query;

public class CustomerQuery {
    private String customerId;

    public CustomerQuery(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
