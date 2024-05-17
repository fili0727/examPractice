package kea.grocery.services;

import kea.grocery.entities.Product;
import kea.grocery.entities.ProductOrder;
import kea.grocery.entities.Van;
import kea.grocery.reposities.ProductOrderRepository;
import kea.grocery.reposities.ProductRepository;
import kea.grocery.reposities.VanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import kea.grocery.services.ProductService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductOrderService {
    private final ProductOrderRepository productOrderRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;


    public ProductOrderService(ProductOrderRepository productOrderRepository, ProductRepository productRepository,  ProductService productService) {
        this.productOrderRepository = productOrderRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    public List<ProductOrder> getAllProductOrders(){
        List<ProductOrder> productOrders = productOrderRepository.findAll();
        return productOrders;
    }

    public Optional<ProductOrder> getProductOrderById(Long id) {
        return productOrderRepository.findById(id);

    }

    public List<ProductOrder> getProductOrdersByProductId(Long productId) {
        List<ProductOrder> allOrders = productOrderRepository.findAll();
        return allOrders.stream()
                .filter(order -> order.getProduct().getId().equals(productId))
                .collect(Collectors.toList());
    }


    public List<ProductOrder> getProductOrdersByIds(List<Long> ids) {
        return productOrderRepository.findAllById(ids);
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

    public ProductOrder editProductOrder(ProductOrder request, Long id) {
        ProductOrder productOrderToEdit = productOrderRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Order not found"));
        updateProductOrder(productOrderToEdit, request);
        productOrderRepository.save(productOrderToEdit);
        return productOrderToEdit;
    }

    private void updateProductOrder(ProductOrder original, ProductOrder request){
        original.setQuantity(request.getQuantity());
    }

    public void deleteProductOrder(Long id) {
        ProductOrder productOrder = productOrderRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Order not found"));
        productOrderRepository.delete(productOrder);
    }

    public ResponseEntity deleteProduct(Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            List<ProductOrder> orders = getProductOrdersByProductId(id);
            if (!orders.isEmpty()) {
                throw new IllegalStateException("Product is part of an order and cannot be deleted");
            }
            productRepository.delete(product.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }






}
