package es.urjc.samples.eventsourcing.shoppingcart.query.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, String> {
}
