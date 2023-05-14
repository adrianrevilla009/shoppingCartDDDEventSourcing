package es.urjc.samples.eventsourcing.shoppingcart;

import es.urjc.samples.eventsourcing.shoppingcart.coreapi.command.CreateCustomerCommand;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.command.CreateShoppingCartCommand;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.query.AllCustomersQuery;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.query.CustomerQuery;
import es.urjc.samples.eventsourcing.shoppingcart.query.customer.CustomerInfo;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public CustomerController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    /* ######################################### ENDPOINTS ################################## */
    @PostMapping
    public Future<Void> createCustomer(@RequestBody CustomerCreation customerCreation) {
        return commandGateway.send(
                new CreateCustomerCommand(
                        customerCreation.getCustomerId() != null ? customerCreation.getCustomerId() : UUID.randomUUID().toString(),
                        customerCreation.getFullName(), customerCreation.getAddress()
                )
        );
    }

    @GetMapping
    public CompletableFuture<List<CustomerInfo>> listCustomers() {
        return queryGateway.query(new AllCustomersQuery(), ResponseTypes.multipleInstancesOf(CustomerInfo.class));
    }

    @GetMapping("/{customerId}")
    public CompletableFuture<CustomerInfo> getCustomer(@PathVariable String customerId) {
        return queryGateway.query(new CustomerQuery(customerId), ResponseTypes.instanceOf(CustomerInfo.class));
    }

    @PostMapping("/{customerId}/cart")
    public Future<Void> createCart(@PathVariable String customerId) {
        Assert.state(customerId != null, "Customer id cant be null");

        return commandGateway.send(
                new CreateShoppingCartCommand(
                        customerId,
                        UUID.randomUUID().toString()
                )
        );
    }

    /* ######################################### DOMAIN ################################## */
    public static class CustomerCreation {
        private String customerId;
        private String fullName;
        private String address;

        public CustomerCreation() {
        }

        public CustomerCreation(String customerId, String fullName, String address) {
            this.customerId = customerId;
            this.fullName = fullName;
            this.address = address;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
