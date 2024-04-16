package kea.grocery.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate deliveryDate;
    private String fromWarehouse; //Replace with actual warehouse entity
    private String toDestination;

    @ManyToOne
    private Van van;

   @OneToMany
    private List<ProductOrder> productOrders;


    public int getTotalWeightInKg(){
        int totalWeight = 0;
        for (ProductOrder productOrder : productOrders) {
            totalWeight += productOrder.getTotalWeightInGrams();
        }
        return (int)Math.ceil((double) totalWeight / 1000);
    }

}
