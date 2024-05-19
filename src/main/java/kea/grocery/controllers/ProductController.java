package kea.grocery.controllers;


import kea.grocery.entities.Product;
import kea.grocery.entities.ProductOrder;
import kea.grocery.services.ProductOrderService;
import kea.grocery.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductOrderService productOrderService;

    public ProductController(ProductService productService, ProductOrderService productOrderService) {
        this.productService = productService;
        this.productOrderService = productOrderService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        return ResponseEntity.of(productService.getProductByName(name));
    }

    @PostMapping
    public Product addProduct(@RequestBody Product request) {
        return productService.addProduct(request);
    }

    @PutMapping("/{id}")
    public Product editProduct(@RequestBody Product request, @PathVariable int id) {
        return productService.editProduct(request,id);
    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity deleteProduct(@PathVariable Long id) {
//            return productService.deleteProduct(id);
//
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable int id) {
        try {
            return productOrderService.deleteProduct(id);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
