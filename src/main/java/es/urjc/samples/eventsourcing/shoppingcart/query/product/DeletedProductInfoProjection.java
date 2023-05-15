package es.urjc.samples.eventsourcing.shoppingcart.query.product;

import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.CreatedDeletedProductEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class DeletedProductInfoProjection {
    private final DeletedProductInfoRepository deletedProductInfoRepository;
    private final ProductInfoRepository productInfoRepository;

    public DeletedProductInfoProjection(DeletedProductInfoRepository deletedProductInfoRepository,
                                        ProductInfoRepository productInfoRepository) {
        this.deletedProductInfoRepository = deletedProductInfoRepository;
        this.productInfoRepository = productInfoRepository;
    }

    @EventHandler
    public void on(CreatedDeletedProductEvent event){
        ProductInfo productInfo = this.productInfoRepository.getById(event.getDeletedProductId());

        DeletedProductInfo deletedProductInfo = new DeletedProductInfo();
        deletedProductInfo.setDeletedProductId(event.getDeletedProductId());
        deletedProductInfo.setName(productInfo.getName());
        deletedProductInfo.setDescription(productInfo.getDescription());
        deletedProductInfo.setPrice(productInfo.getPrice());
        deletedProductInfo.setCartId(event.getCartId());
        deletedProductInfo.setProductId(event.getProductId());
        System.out.println("Deleted product saved: " + deletedProductInfo);
        deletedProductInfoRepository.save(deletedProductInfo);
    }
}
