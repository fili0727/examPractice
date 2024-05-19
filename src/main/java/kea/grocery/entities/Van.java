package kea.grocery.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Entity
@Setter
@Getter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Van {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private int capacityInKg;

    @OneToMany(mappedBy = "van")
    private List<Delivery> deliveries;


    public Van(String brand, String model, int capacityInKg) {
        this.brand = brand;
        this.model = model;
        this.capacityInKg = capacityInKg;
        this.deliveries = new ArrayList<>();
    }

    public int getCombinedWeightOfDeliveriesInKg() {
        int totalWeightOfDeliveries = 0;
        for (Delivery delivery : deliveries) {
            totalWeightOfDeliveries += delivery.getTotalWeightInKg();
        }
        return totalWeightOfDeliveries;
    }

    public boolean canAddDelivery(Delivery delivery) {
        return getCombinedWeightOfDeliveriesInKg() + delivery.getTotalWeightInKg() < capacityInKg;
    }
}
