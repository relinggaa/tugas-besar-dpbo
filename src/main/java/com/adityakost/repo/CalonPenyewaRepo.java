package com.adityakost.repo;

import com.adityakost.entity.CalonPenyewa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalonPenyewaRepo extends JpaRepository<CalonPenyewa, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    CalonPenyewa findByEmailAndPassword(String email, String password);
    
}
