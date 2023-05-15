package es.urjc.samples.eventsourcing.shoppingcart.query.product;

import es.urjc.samples.eventsourcing.shoppingcart.query.shoppingCart.CartItemInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeletedProductInfoRepository extends JpaRepository<DeletedProductInfo, String> {
    @Query(value = "SELECT c FROM DeletedProductInfo c WHERE c.cartId = ?1 ")
    List<CartItemInfo> getByCart(String cartId);
}
