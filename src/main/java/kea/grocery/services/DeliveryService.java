package kea.grocery.services;

import kea.grocery.entities.Delivery;
import kea.grocery.entities.ProductOrder;
import kea.grocery.entities.Van;
import kea.grocery.reposities.DeliveryRepository;
import kea.grocery.reposities.ProductOrderRepository;
import kea.grocery.reposities.VanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final ProductOrderRepository productOrderRepository;
    private final VanRepository vanRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, ProductOrderRepository productOrderRepository, VanRepository vanRepository) {
        this.deliveryRepository = deliveryRepository;
        this.productOrderRepository = productOrderRepository;
        this.vanRepository = vanRepository;
    }

    public List<Delivery> getAllDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        return deliveries;
    }

    public Delivery getDeliveryById(int id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Delivery not found"));
        return delivery;
    }



}
