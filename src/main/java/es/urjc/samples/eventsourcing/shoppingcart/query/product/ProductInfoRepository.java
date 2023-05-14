package es.urjc.samples.eventsourcing.shoppingcart.query.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
}
