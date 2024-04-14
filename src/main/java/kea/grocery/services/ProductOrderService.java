package kea.grocery.services;

import kea.grocery.entities.Product;
import kea.grocery.entities.ProductOrder;
import kea.grocery.entities.Van;
import kea.grocery.reposities.ProductOrderRepository;
import kea.grocery.reposities.ProductRepository;
import kea.grocery.reposities.VanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import kea.grocery.services.ProductService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductOrderService {
    private final ProductOrderRepository productOrderRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;


    public ProductOrderService(ProductOrderRepository productOrderRepository, ProductRepository productRepository, VanRepository vanRepository, ProductService productService) {
        this.productOrderRepository = productOrderRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    public List<ProductOrder> getAllProductOrders(){
        List<ProductOrder> productOrders = productOrderRepository.findAll();
        return productOrders;
    }

    public ProductOrder getProductOrderById(int id) {
        ProductOrder productOrder = productOrderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product Order not found"));
        return productOrder;
    }


    public ProductOrder createProductOrder(Long productId, int quantity) {
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        ProductOrder productOrder = new ProductOrder();
        productOrder.setProduct(product);
        productOrder.setQuantity(quantity);
        return productOrderRepository.save(productOrder);
    }






}
