package es.urjc.samples.eventsourcing.shoppingcart;

import es.urjc.samples.eventsourcing.shoppingcart.coreapi.command.CreateProductCommand;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.query.AllProductsQuery;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.query.ProductQuery;
import es.urjc.samples.eventsourcing.shoppingcart.query.product.ProductInfo;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public ProductController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    /* ######################################### ENDPOINTS ################################## */
    @PostMapping
    public Future<Void> addProduct(@RequestBody ProductCreation productCreation) {
        Assert.state(productCreation.getPrice().doubleValue() > 0, "Price of a product can't be less or equal to 0");

        return commandGateway.send(
                new CreateProductCommand(
                        productCreation.getProductId() != null ? productCreation.getProductId() : UUID.randomUUID().toString(),
                        productCreation.getName(), productCreation.getDescription(), productCreation.getPrice()
                )
        );
    }

    @GetMapping
    public CompletableFuture<List<ProductInfo>> listProducts() {
        return queryGateway.query(new AllProductsQuery(), ResponseTypes.multipleInstancesOf(ProductInfo.class));
    }

    @GetMapping("/{productId}")
    public CompletableFuture<ProductInfo> getProduct(@PathVariable String productId) {
        return queryGateway.query(new ProductQuery(productId), ResponseTypes.instanceOf(ProductInfo.class));
    }



    /* ######################################### DOMAIN ################################## */
    public static class ProductCreation {
        private String productId;
        private String name;
        private String description;
        private BigDecimal price;

        public ProductCreation(String productId, String name, String description, BigDecimal price) {
            this.productId = productId;
            this.name = name;
            this.description = description;
            this.price = price;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
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
    }
}
