package es.urjc.samples.eventsourcing.shoppingcart.query.shoppingCart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemInfoRepository extends JpaRepository<CartItemInfo, String> {
}
