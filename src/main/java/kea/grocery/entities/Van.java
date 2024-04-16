package kea.grocery.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Van {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private int capacityInKg;

    @OneToMany
    private List<Delivery> deliveries;


    public int getCombinedWeightOfDeliveriesInKg(){
        int totalWeightOfDeliveries = 0;
        for (Delivery delivery : deliveries) {
            totalWeightOfDeliveries += delivery.getTotalWeightInKg();
        }
        return totalWeightOfDeliveries;
    }

    public boolean canAddDelivery(Delivery delivery){
        return getCombinedWeightOfDeliveriesInKg() + delivery.getTotalWeightInKg() < capacityInKg;
    }
}
