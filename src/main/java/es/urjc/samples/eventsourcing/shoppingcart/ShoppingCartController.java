package es.urjc.samples.eventsourcing.shoppingcart;

import es.urjc.samples.eventsourcing.shoppingcart.coreapi.command.AddItemCommand;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.command.CreateDeletedProductCommand;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.command.RemoveItemCommand;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.CreatedDeletedProductEvent;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.CreatedProductEvent;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.query.AllShoppingCartsQuery;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.query.DeletedProductsQuery;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.query.ShoppingCartQuery;
import es.urjc.samples.eventsourcing.shoppingcart.query.product.ProductInfo;
import es.urjc.samples.eventsourcing.shoppingcart.query.shoppingCart.CartItemInfo;
import es.urjc.samples.eventsourcing.shoppingcart.query.shoppingCart.ShoppingCartInfo;
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
@RequestMapping("/carts")
public class ShoppingCartController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public ShoppingCartController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    /* ######################################### ENDPOINTS ################################## */
    @GetMapping
    public CompletableFuture<List<ShoppingCartInfo>> listAllCarts(){
        return queryGateway.query(new AllShoppingCartsQuery(), ResponseTypes.multipleInstancesOf(ShoppingCartInfo.class));
    }

    @GetMapping("/{cartId}")
    public CompletableFuture<ShoppingCartInfo> getCart(@PathVariable String cartId) {
        return queryGateway.query(new ShoppingCartQuery(cartId), ResponseTypes.instanceOf(ShoppingCartInfo.class));
    }

    @PostMapping("/{cartId}/product/{productId}")
    public Future<Void> addItem(@PathVariable String cartId, @PathVariable String productId, @RequestParam int quantity) {
        Assert.state(cartId != null, "Cart id cant be null");
        Assert.state(productId != null, "Product id cant be null");

        return commandGateway.send(
                new AddItemCommand(
                        cartId,
                        productId,
                        quantity
                )
        );
    }

    @DeleteMapping("/{cartId}/product/{productId}")
    public Future<Void> removeItem(@PathVariable String cartId, @PathVariable String productId, @RequestParam int quantity) {
        Assert.state(cartId != null, "Cart id cant be null");
        Assert.state(productId != null, "Product id cant be null");

        commandGateway.send(
                new CreateDeletedProductCommand(
                        UUID.randomUUID().toString(),
                        cartId,
                        productId,
                        quantity
                )
        );

        return commandGateway.send(
                new RemoveItemCommand(
                        cartId,
                        productId,
                        quantity
                )
        );
    }

    @GetMapping("/{cartId}/deletedProducts")
    public CompletableFuture<List<CartItemInfo>> getDeletedProducts(@PathVariable String cartId) {
        return queryGateway.query(new DeletedProductsQuery(cartId), ResponseTypes.multipleInstancesOf(CartItemInfo.class));
    }

    /* ######################################### DOMAIN ################################## */
}
