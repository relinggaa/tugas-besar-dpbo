package com.adityakost.service;

import com.adityakost.entity.CalonPenyewa;
import com.adityakost.repo.CalonPenyewaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalonPenyewaService {

    private final CalonPenyewaRepo calonPenyewaRepo;

    @Autowired
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
}
