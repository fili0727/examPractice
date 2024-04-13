package kea.grocery.reposities;

import kea.grocery.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>{
   Optional<Product> findByName(String name);
}
