package es.urjc.samples.eventsourcing.shoppingcart.query.shoppingCart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemInfoRepository extends JpaRepository<CartItemInfo, String> {
}
