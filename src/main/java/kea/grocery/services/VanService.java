package kea.grocery.services;

import kea.grocery.entities.Delivery;
import kea.grocery.entities.Van;
import kea.grocery.reposities.VanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class VanService {
    private VanRepository vanRepository;

    public VanService(VanRepository vanRepository) {
        this.vanRepository = vanRepository;
    }

    public List<Van> getAll() {
        return vanRepository.findAll();
    }


        public Optional<Van> findVanById(Long id) {
            return vanRepository.findById(id);
        }

        public boolean addDeliveryToVan (Delivery delivery, Long vanId){
          Van van = vanRepository.findById(vanId).orElseThrow();
          return addDeliveryToVan(delivery, van);
        }

        public boolean addDeliveryToVan(Delivery delivery, Van van) {

        if(van.getCombinedWeightOfDeliveriesInKg() + delivery.getTotalWeightInKg() < van.getCapacityInKg()) {
            van.getDeliveries().add(delivery);
        } else{
            return false;
        }


            vanRepository.save(van);
        return true;
        }
}

