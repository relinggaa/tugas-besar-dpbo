package com.adityakost.service;

import com.adityakost.entity.Gambar;
import com.adityakost.repo.GambarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Base64;

@Service
public class GambarService {

    @Autowired
    private GambarRepo gambarRepository;

    public Gambar getGambarByKamarId(Long kamarId) {
        return gambarRepository.findByKamarId(kamarId);
    }

    public String getGambarBase64(Long kamarId) {
        Gambar gambar = gambarRepository.findByKamarId(kamarId);
        return gambar != null ? Base64.getEncoder().encodeToString(gambar.getDataGambar()) : null;
    }
}

