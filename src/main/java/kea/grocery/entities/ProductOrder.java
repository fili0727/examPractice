package kea.grocery.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@NoArgsConstructor
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)  // FetchType.EAGER is used to load the related entity immediately
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY is used to load the related entity only when it is accessed
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    private int quantity;

    public ProductOrder(Product product, Delivery delivery, int quantity) {
        this.product = product;
        this.delivery = delivery;
        this.quantity = quantity;
    }

    public int getTotalWeightInGrams() {
        return product.getWeightInGrams() * quantity;
    }
}
