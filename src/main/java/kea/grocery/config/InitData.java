package kea.grocery.config;

import kea.grocery.entities.Delivery;
import kea.grocery.entities.Product;
import kea.grocery.entities.ProductOrder;
import kea.grocery.entities.Van;
import kea.grocery.reposities.DeliveryRepository;
import kea.grocery.reposities.ProductOrderRepository;
import kea.grocery.reposities.ProductRepository;
import kea.grocery.reposities.VanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;
    private final DeliveryRepository deliveryRepository;
    private final VanRepository vanRepository;

    public InitData(ProductRepository productRepository, ProductOrderRepository productOrderRepository, DeliveryRepository deliveryRepository, VanRepository vanRepository) {
        this.productRepository = productRepository;
        this.productOrderRepository = productOrderRepository;
        this.deliveryRepository = deliveryRepository;
        this.vanRepository = vanRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initAllData();
    }


    private void initAllData() {
        Product banana = new Product(1,"Banana", 10, 100);
        Product apple = new Product(2,"Apple", 5, 50);
        Product orange = new Product(3,"Orange", 7, 70);
        Product pear = new Product(4,"Pear", 8, 80);

        List<Product> products = List.of(banana, apple, orange, pear);
        productRepository.saveAll(products);

        Van van1 = new Van("Ford", "Transit", 2000);
        Van van2 = new Van("Mercedes", "Sprinter", 2500);

        vanRepository.saveAll(List.of(van1, van2));

        Delivery delivery1 = new Delivery(LocalDate.now(), "Warehouse A", "Destination A", van1);
        Delivery delivery2 = new Delivery(LocalDate.now(), "Warehouse B", "Destination B", van2);

        deliveryRepository.saveAll(List.of(delivery1, delivery2));


        ProductOrder order1 = new ProductOrder(banana, delivery1, 10);
        ProductOrder order2 = new ProductOrder(apple, delivery1, 20);
        ProductOrder order3 = new ProductOrder(orange, delivery2, 15);
        ProductOrder order4 = new ProductOrder(pear, delivery2, 25);

        productOrderRepository.saveAll(List.of(order1, order2, order3, order4));

    }

}
