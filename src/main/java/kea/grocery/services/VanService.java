package kea.grocery.services;

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


}

