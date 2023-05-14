package es.urjc.samples.eventsourcing.shoppingcart.query.product;

import es.urjc.samples.eventsourcing.shoppingcart.coreapi.event.CreatedProductEvent;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.query.AllProductsQuery;
import es.urjc.samples.eventsourcing.shoppingcart.coreapi.query.ProductQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductInfoProjection {
    private final ProductInfoRepository productInfoRepository;

    public ProductInfoProjection(ProductInfoRepository productInfoRepository) {
        this.productInfoRepository = productInfoRepository;
    }

    @EventHandler
    public void on(CreatedProductEvent event){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(event.getProductId());
        productInfo.setName(event.getName());
        productInfo.setDescription(event.getDescription());
        productInfo.setPrice(event.getPrice());
        System.out.println("Product created: " + productInfo);
        productInfoRepository.save(productInfo);
    }

    @QueryHandler
    public List<ProductInfo> handle(AllProductsQuery query) {
        System.out.println("All products query executed");
        return productInfoRepository.findAll();
    }

    @QueryHandler
    public ProductInfo handle(ProductQuery query) {
        System.out.println("Get product query executed");
        return productInfoRepository.getById(query.getProductId());
    }
}
