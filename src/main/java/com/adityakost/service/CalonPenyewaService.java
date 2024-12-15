package com.adityakost.service;

import com.adityakost.entity.CalonPenyewa;
import com.adityakost.repo.CalonPenyewaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class CalonPenyewaService {

    private final CalonPenyewaRepo calonPenyewaRepo;

    
    public CalonPenyewaService(CalonPenyewaRepo calonPenyewaRepo) {
        this.calonPenyewaRepo = calonPenyewaRepo;
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
}