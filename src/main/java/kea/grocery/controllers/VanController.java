package kea.grocery.controllers;

import kea.grocery.entities.Delivery;
import kea.grocery.entities.Van;
import kea.grocery.services.VanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vans")
public class VanController {

    private final VanService vanService;

    public VanController(VanService vanService) {
        this.vanService = vanService;
    }

    @PostMapping("{vanId}/deliveries")
    public ResponseEntity<Van> addDeliveryToVan(@PathVariable Long vanId, @RequestBody Delivery delivery) {
        Van van = vanService.findVanById(vanId).orElseThrow(); // 404 hvis van ik findes
       if(vanService.addDeliveryToVan(delivery, vanId)) {
         return   ResponseEntity.ok(van);
       }else{
             return ResponseEntity.badRequest().build(); // Fejl 400 bad request hvis van er fyldt
       }


    }
}
