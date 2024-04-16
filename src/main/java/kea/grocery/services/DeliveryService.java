package kea.grocery.services;

import jakarta.transaction.Transactional;
import kea.grocery.dto.DeliveryDTO;
import kea.grocery.entities.Delivery;
import kea.grocery.entities.ProductOrder;
import kea.grocery.entities.Van;
import kea.grocery.reposities.DeliveryRepository;
import kea.grocery.reposities.ProductOrderRepository;
import kea.grocery.reposities.VanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final ProductOrderService productOrderService;
    private final VanService vanService;

    public DeliveryService(DeliveryRepository deliveryRepository, ProductOrderService productOrderService, VanService vanService) {
        this.deliveryRepository = deliveryRepository;
        this.productOrderService = productOrderService;
        this.vanService = vanService;
    }


    public List<Delivery> getDeliveriesByVan(Long vanId) {
        return deliveryRepository.findAllByVanId(vanId);
    }

    public Delivery createDelivery(DeliveryDTO deliveryDTO) {
        Delivery delivery = new Delivery();
        delivery.setDeliveryDate(deliveryDTO.getDeliveryDate());
        delivery.setFromWarehouse(deliveryDTO.getFromWarehouse());
        delivery.setToDestination(deliveryDTO.getToDestination());

        if (deliveryDTO.getVanId() != null) {
            Van van = vanService.findVanById(deliveryDTO.getVanId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Van not found"));
            delivery.setVan(van);
        }


        return deliveryRepository.save(delivery);
    }




    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Delivery not found"));
    }




    public Delivery addProductOrdersToDelivery(Long deliveryId, List<Long> productOrderIds) {
        Delivery delivery = getDeliveryById(deliveryId);
        List<ProductOrder> productOrdersToAdd = productOrderService.getProductOrdersByIds(productOrderIds);

        for (ProductOrder productOrder : productOrdersToAdd) {
            if (!delivery.getProductOrders().contains(productOrder)) {
                delivery.getProductOrders().add(productOrder);
            } else {
                throw new IllegalStateException("Product order already added to this delivery");
            }
        }
        delivery.getTotalWeightInKg();
        return deliveryRepository.save(delivery);
    }





    public Delivery updateDelivery(Delivery delivery) {
        delivery.getTotalWeightInKg();
        return deliveryRepository.save(delivery);
    }

    public void deleteDelivery(Long id) {
        deliveryRepository.deleteById(id);
    }

}

