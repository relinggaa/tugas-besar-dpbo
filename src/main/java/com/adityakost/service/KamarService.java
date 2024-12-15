package com.adityakost.service;

import com.adityakost.entity.Kamar;

import com.adityakost.repo.KamarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.io.IOException;
import java.util.List;

@Service

public class KamarService {

    private final KamarRepo kamarRepo;
    private final GambarService gambarService;

    @Autowired
    public KamarService(KamarRepo kamarRepo, GambarService gambarService) {
        this.kamarRepo = kamarRepo;
        this.gambarService = gambarService;
    }

    public List<Kamar> getAllKamar() {
        return kamarRepo.findAll();
    }

    public Kamar getKamarById(Long idKamar) {
        return kamarRepo.findById(idKamar).orElse(null);
    }

    public void saveKamar(Kamar kamar) {
        kamarRepo.save(kamar);
    }

    public void saveKamarWithGambar(String type, float harga, MultipartFile gambarFile) throws IOException {
        if (harga <= 0) {
            throw new IllegalArgumentException("Harga harus lebih besar dari 0.");
        }

        // Membuat entitas Kamar
        Kamar kamar = new Kamar();
        kamar.setType(type);
        kamar.setHarga(harga);
        saveKamar(kamar);

        // Menyimpan gambar jika ada
        if (!gambarFile.isEmpty()) {
            gambarService.saveGambarWithKamar(kamar, gambarFile);
        }
    }
    public void updateKamar(Long id, Kamar kamar) {
        Kamar existingKamar = kamarRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Kamar tidak ditemukan!"));
        existingKamar.setType(kamar.getType());
        existingKamar.setHarga(kamar.getHarga());
        existingKamar.setGambar(kamar.getGambar()); // Memperbarui gambar jika ada
        kamarRepo.save(existingKamar);
    }
    
    
}
