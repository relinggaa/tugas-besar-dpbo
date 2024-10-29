package com.adityakost.service;

import com.adityakost.entity.Pemesanan;
import com.adityakost.repo.PemesananRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PemesananService {

    @Autowired
    private PemesananRepo pemesananRepo;

    public void savePemesanan(Pemesanan pemesanan) {
        pemesananRepo.save(pemesanan);
    }

    public Pemesanan findById(Long id) {
        return pemesananRepo.findById(id).orElse(null);
    }
}
