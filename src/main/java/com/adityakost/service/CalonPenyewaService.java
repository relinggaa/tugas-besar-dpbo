package com.adityakost.service;

import com.adityakost.entity.CalonPenyewa;
import com.adityakost.entity.Pemesanan;
import com.adityakost.repo.CalonPenyewaRepo;
import com.adityakost.repo.PemesananRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalonPenyewaService {

    private final CalonPenyewaRepo calonPenyewaRepo;
    private final PemesananRepo pemesananRepo;

    public CalonPenyewaService(CalonPenyewaRepo calonPenyewaRepo, PemesananRepo pemesananRepo) {
        this.calonPenyewaRepo = calonPenyewaRepo;
        this.pemesananRepo = pemesananRepo;
    }

    public void saveCalonPenyewa(CalonPenyewa calonPenyewa) {
        calonPenyewaRepo.save(calonPenyewa);
    }

    public boolean usernameExists(String username) {
        return calonPenyewaRepo.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return calonPenyewaRepo.existsByEmail(email);
    }

    public boolean phoneNumberExists(String phoneNumber) {
        return calonPenyewaRepo.existsByPhoneNumber(phoneNumber);
    }

    public List<CalonPenyewa> getAllCalonPenyewa() {
        return calonPenyewaRepo.findAll();
    }

    public CalonPenyewa getCalonPenyewaById(Long id) {
        return calonPenyewaRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void updateCalonPenyewa(CalonPenyewa calonPenyewa) {
        calonPenyewaRepo.save(calonPenyewa);
    }

    public CalonPenyewa getCalonPenyewaByEmailAndPassword(String email, String password) {
        return calonPenyewaRepo.findByEmailAndPassword(email, password);
    }

    public void saveComplain(String complain) {
        if (complain == null || complain.isEmpty()) {
            throw new IllegalArgumentException("Komplain tidak boleh kosong!");
        }
        System.out.println("Komplain disimpan: " + complain);
    }

    public List<CalonPenyewa> getCalonPenyewaWithComplain() {
        return calonPenyewaRepo.findAll()
                .stream()
                .filter(p -> p.getComplain() != null && !p.getComplain().trim().isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Mendapatkan Calon Penyewa yang memiliki pemesanan kamar.
     */
    public List<Pemesanan> getPenghuniDenganKamar() {
        return pemesananRepo.findAll();
    }
}
