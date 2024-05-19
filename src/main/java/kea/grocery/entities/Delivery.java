package kea.grocery.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate deliveryDate;
    private String fromWarehouse;
    private String toDestination;

    @ManyToOne
    private Van van;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOrder> productOrders;


    public Delivery(LocalDate deliveryDate, String fromWarehouse, String toDestination, Van van) {
        this.deliveryDate = deliveryDate;
        this.fromWarehouse = fromWarehouse;
        this.toDestination = toDestination;
        this.van = van;
        this.productOrders = new ArrayList<>();
    }

    public int getTotalWeightInKg() {
        int totalWeight = 0;
        for (ProductOrder productOrder : productOrders) {
            totalWeight += productOrder.getTotalWeightInGrams();
        }
        return (int) Math.ceil((double) totalWeight / 1000);
    }
}
