package kea.grocery.controllers;

import kea.grocery.dto.DeliveryDTO;
import kea.grocery.entities.Delivery;
import kea.grocery.services.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;



import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@RequestBody DeliveryDTO delivery) {
        try {
            Delivery newDelivery = deliveryService.createDelivery(delivery);
            return ResponseEntity.ok(newDelivery);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDelivery(@PathVariable Long id) {
        try {
            Delivery delivery = deliveryService.getDeliveryById(id);
            return ResponseEntity.ok(delivery);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


//    @PostMapping("/{deliveryId}/addProductOrder/{productOrderId}")
//    public ResponseEntity<Delivery> addProductOrderToDelivery(@PathVariable Long deliveryId, @PathVariable Long productOrderId) {
//        try {
//            Delivery updatedDelivery = deliveryService.addProductOrderToDelivery(deliveryId, productOrderId);
//            return ResponseEntity.ok(updatedDelivery);
//        } catch (ResponseStatusException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }


    @GetMapping("/van/{vanId}")
    public ResponseEntity<List<Delivery>> getDeliveriesByVan(@PathVariable Long vanId) {
        List<Delivery> deliveries = deliveryService.getDeliveriesByVan(vanId);
        return ResponseEntity.ok(deliveries);
    }

    @PostMapping("/{deliveryId}/addProductOrders")
    public ResponseEntity<Delivery> addProductOrdersToDelivery(@PathVariable Long deliveryId, @RequestBody List<Long> productOrderIds) {
        try {
            Delivery updatedDelivery = deliveryService.addProductOrdersToDelivery(deliveryId, productOrderIds);
            return ResponseEntity.ok(updatedDelivery);
        } catch (IllegalStateException e)  {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        List<Delivery> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }
}

