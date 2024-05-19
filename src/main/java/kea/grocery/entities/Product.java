package kea.grocery.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double price;
    private int weightInGrams;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductOrder> productOrders;


    public Product(int id,String name, double price, int weightInGrams) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weightInGrams = weightInGrams;
        this.productOrders = new ArrayList<>();
    }

    public Product(String name, double price, int weightInGrams) {
        this.name = name;
        this.price = price;
        this.weightInGrams = weightInGrams;
        this.productOrders = new ArrayList<>();
    }
}
