package es.urjc.samples.eventsourcing.shoppingcart.query.customer;

import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.CreatedCustomerEvent;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.CreatedShoppingCartEvent;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.query.AllCustomersQuery;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.query.CustomerQuery;
import es.urjc.samples.eventsourcing.shoppingcart.query.shoppingCart.ShoppingCartInfo;
import es.urjc.samples.eventsourcing.shoppingcart.query.shoppingCart.ShoppingCartInfoRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerInfoProjection {
    private final CustomerInfoRepository customerInfoRepository;
    private final ShoppingCartInfoRepository shoppingCartInfoRepository;

    public CustomerInfoProjection(CustomerInfoRepository customerInfoRepository,
                                  ShoppingCartInfoRepository shoppingCartInfoRepository) {
        this.customerInfoRepository = customerInfoRepository;
        this.shoppingCartInfoRepository = shoppingCartInfoRepository;
    }

    @EventHandler
    public void on(CreatedCustomerEvent event){
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setCustomerId(event.getCustomerId());
        customerInfo.setFullName(event.getFullName());
        customerInfo.setAddress(event.getAddress());
        System.out.println("Customer created: " + customerInfo);
        customerInfoRepository.save(customerInfo);
    }

    @EventHandler
    public void on(CreatedShoppingCartEvent event) {
        ShoppingCartInfo shoppingCartInfo = new ShoppingCartInfo();
        shoppingCartInfo.setCartId(event.getCartId());
        shoppingCartInfo.setCustomerId(event.getCustomerId());
        System.out.println("Shopping cart created: " + shoppingCartInfo);
        shoppingCartInfoRepository.save(shoppingCartInfo);
    }

    @QueryHandler
    public List<CustomerInfo> handle(AllCustomersQuery query) {
        System.out.println("All customers query executed");
        return customerInfoRepository.findAll();
    }

    @QueryHandler
    public CustomerInfo handle(CustomerQuery query) {
        System.out.println("Get customer query executed");
        return customerInfoRepository.getById(query.getCustomerId());
    }
}
