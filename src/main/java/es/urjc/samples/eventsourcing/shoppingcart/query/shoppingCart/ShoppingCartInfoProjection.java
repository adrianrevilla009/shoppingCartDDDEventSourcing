package es.urjc.samples.eventsourcing.shoppingcart.query.shoppingCart;

import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.AddedItemEvent;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.CreatedCustomerEvent;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.CreatedShoppingCartEvent;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.RemovedItemEvent;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.query.*;
import es.urjc.samples.eventsourcing.shoppingcart.query.customer.CustomerInfo;
import es.urjc.samples.eventsourcing.shoppingcart.query.product.DeletedProductInfo;
import es.urjc.samples.eventsourcing.shoppingcart.query.product.DeletedProductInfoRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ShoppingCartInfoProjection {
    private final ShoppingCartInfoRepository shoppingCartInfoRepository;
    private final DeletedProductInfoRepository deletedProductInfoRepository;

    public ShoppingCartInfoProjection(ShoppingCartInfoRepository shoppingCartInfoRepository,
                                      DeletedProductInfoRepository deletedProductInfoRepository) {
        this.shoppingCartInfoRepository = shoppingCartInfoRepository;
        this.deletedProductInfoRepository = deletedProductInfoRepository;
    }

    @EventHandler
    public void on(AddedItemEvent event){
        ShoppingCartInfo shoppingCartInfo = shoppingCartInfoRepository.getById(event.getCartId());

        Optional<CartItemInfo> cartItem = getCartItem(shoppingCartInfo, event.getProductId());

        // keep track of deleted products
        cartItem.ifPresentOrElse(
                item -> item.setQuantity(item.getQuantity() + event.getQuantity()),
                () -> shoppingCartInfo.addItem(new CartItemInfo(event.getProductId(), event.getQuantity(), event.getCartId()))
        );

        System.out.println("Item added to shopping cart: " + shoppingCartInfo);
        shoppingCartInfoRepository.save(shoppingCartInfo);
    }

    @EventHandler
    public void on(RemovedItemEvent event){
        ShoppingCartInfo shoppingCartInfo = shoppingCartInfoRepository.getById(event.getCartId());

        Optional<CartItemInfo> cartItem = getCartItem(shoppingCartInfo, event.getProductId());

        if (event.getQuantity() == 0 || event.getQuantity() >= cartItem.get().getQuantity()) {
            shoppingCartInfo.getItems().remove(cartItem);
        } else {
            cartItem.get().setQuantity(cartItem.get().getQuantity() - event.getQuantity());
        }

        System.out.println("Item removed to shopping cart: " + shoppingCartInfo);
        shoppingCartInfoRepository.save(shoppingCartInfo);
    }

    @QueryHandler
    public List<ShoppingCartInfo> handle(AllShoppingCartsQuery query) {
        System.out.println("All shopping carts query executed");
        return shoppingCartInfoRepository.findAll();
    }

    @QueryHandler
    public ShoppingCartInfo handle(ShoppingCartQuery query) {
        System.out.println("Get customer query executed");
        return shoppingCartInfoRepository.getById(query.getCartId());
    }

    @QueryHandler
    public List<CartItemInfo> handle(DeletedProductsQuery query) {
        System.out.println("Get deleted products query executed");
        return deletedProductInfoRepository.getByCart(query.getCartId());
    }

    private Optional<CartItemInfo> getCartItem(ShoppingCartInfo shoppingCartInfo, String productId) {
        Optional<CartItemInfo> cartItem = shoppingCartInfo.getItems()
                .stream()
                .filter(item -> item.getProductId().equalsIgnoreCase(productId))
                .findFirst();

        return cartItem;
    }
}
