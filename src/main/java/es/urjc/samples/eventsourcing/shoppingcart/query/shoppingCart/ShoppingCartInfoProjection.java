package es.urjc.samples.eventsourcing.shoppingcart.query.shoppingCart;

import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.AddedItemEvent;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.CreatedCustomerEvent;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.CreatedShoppingCartEvent;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.RemovedItemEvent;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.query.*;
import es.urjc.samples.eventsourcing.shoppingcart.query.customer.CustomerInfo;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ShoppingCartInfoProjection {
    private final ShoppingCartInfoRepository shoppingCartInfoRepository;
    private final CartItemInfoRepository cartItemInfoRepository;

    public ShoppingCartInfoProjection(ShoppingCartInfoRepository shoppingCartInfoRepository,
                                      CartItemInfoRepository cartItemInfoRepository) {
        this.shoppingCartInfoRepository = shoppingCartInfoRepository;
        this.cartItemInfoRepository = cartItemInfoRepository;
    }

    @EventHandler
    public void on(AddedItemEvent event){
        ShoppingCartInfo shoppingCartInfo = shoppingCartInfoRepository.getById(event.getCartId());

        Optional<CartItemInfo> cartItem = getCartItem(shoppingCartInfo, event.getProductId());

        cartItem.ifPresentOrElse(
                item -> item.setQuantity(item.getQuantity() + event.getQuantity()),
                () -> shoppingCartInfo.addItem(new CartItemInfo(event.getProductId(), event.getQuantity()))
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

        // keep track of deleted products
        saveDeletedProduct(cartItem.get());
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
        return cartItemInfoRepository.getByCart(query.getCartId());
    }

    private Optional<CartItemInfo> getCartItem(ShoppingCartInfo shoppingCartInfo, String productId) {
        Optional<CartItemInfo> cartItem = shoppingCartInfo.getItems()
                .stream()
                .filter(item -> item.getProductId().equalsIgnoreCase(productId))
                .findFirst();

        return cartItem;
    }

    private void saveDeletedProduct(CartItemInfo cartItemInfo) {
        cartItemInfoRepository.save(cartItemInfo);
    }
}
