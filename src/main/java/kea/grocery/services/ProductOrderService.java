package kea.grocery.services;

import kea.grocery.entities.Product;
import kea.grocery.entities.ProductOrder;
import kea.grocery.reposities.ProductOrderRepository;
import kea.grocery.reposities.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    public Optional<ProductOrder> getProductOrderById(int id) {
        return productOrderRepository.findById(id);

    }

    public List<ProductOrder> getProductOrdersByIds(List<Integer> ids) {
        return productOrderRepository.findAllById(ids);
    }


    public ProductOrder createProductOrder(int productId, int quantity) {
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

    public ProductOrder editProductOrder(ProductOrder request, int id) {
        ProductOrder productOrderToEdit = productOrderRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Order not found"));
        updateProductOrder(productOrderToEdit, request);
        productOrderRepository.save(productOrderToEdit);
        return productOrderToEdit;
    }

    private void updateProductOrder(ProductOrder original, ProductOrder request){
        original.setQuantity(request.getQuantity());
    }

    public void deleteProductOrder(int id) {
        ProductOrder productOrder = productOrderRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Order not found"));
        productOrderRepository.delete(productOrder);
    }

    public ResponseEntity deleteProduct(int id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            List<ProductOrder> orders = ProductRepository.getProductOrdersByProductId(id);
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
