package com.adityakost.service;

import com.adityakost.entity.Kamar;

import com.adityakost.repo.KamarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class KamarService {

    private final KamarRepo kamarRepo;

    @Autowired
    public KamarService(KamarRepo kamarRepo) {
        this.kamarRepo = kamarRepo;
    }

    public List<Kamar> getAllKamar() {
        return kamarRepo.findAll();
    }
  
    public Kamar getKamarById(Long idKamar) {
        Optional<Kamar> kamar = kamarRepo.findById(idKamar);
        return kamar.orElse(null);
    }

  

}
