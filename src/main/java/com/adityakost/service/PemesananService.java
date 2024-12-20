package com.adityakost.service;

import com.adityakost.entity.CalonPenyewa;
import com.adityakost.entity.Kamar;
import com.adityakost.entity.Pemesanan;
import com.adityakost.repo.CalonPenyewaRepo;
import com.adityakost.repo.KamarRepo;
import com.adityakost.repo.PemesananRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PemesananService {

    private final PemesananRepo pemesananRepo;
    private final KamarRepo kamarRepo;
    private final CalonPenyewaRepo calonPenyewaRepo;

    public PemesananService(PemesananRepo pemesananRepo, KamarRepo kamarRepo, CalonPenyewaRepo calonPenyewaRepo) {
        this.pemesananRepo = pemesananRepo;
        this.kamarRepo = kamarRepo;
        this.calonPenyewaRepo = calonPenyewaRepo;
    }

    /**
     * Simpan pemesanan setelah memastikan CalonPenyewa dan Kamar dikelola oleh Hibernate.
     */
    public void savePemesanan(Pemesanan pemesanan, Long idCalonPenyewa, Long idKamar) {
        CalonPenyewa calonPenyewa = calonPenyewaRepo.findById(idCalonPenyewa)
                .orElseThrow(() -> new RuntimeException("Calon Penyewa tidak ditemukan dengan ID: " + idCalonPenyewa));
        
        Kamar kamar = kamarRepo.findById(idKamar)
                .orElseThrow(() -> new RuntimeException("Kamar tidak ditemukan dengan ID: " + idKamar));
    
        pemesanan.setCalonPenyewa(calonPenyewa);
        pemesanan.setKamar(kamar);
    
        pemesananRepo.save(pemesanan);
    }
    
    

    /**
     * Ambil semua pemesanan.
     */
    public List<Pemesanan> getAllPemesanan() {
        return pemesananRepo.findAll();
    }

    /**
     * Ambil pemesanan berdasarkan ID.
     */
    public Pemesanan getPemesananById(Long id) {
        return pemesananRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pemesanan tidak ditemukan dengan ID: " + id));
    }

    /**
     * Hapus pemesanan berdasarkan ID.
     */
    public void deletePemesananById(Long id) {
        if (!pemesananRepo.existsById(id)) {
            throw new RuntimeException("Pemesanan tidak ditemukan dengan ID: " + id);
        }
        pemesananRepo.deleteById(id);
    }

    /**
     * Ambil semua pemesanan berdasarkan ID Kamar.
     */
    public List<Pemesanan> getPemesananByKamarId(Long idKamar) {
        return pemesananRepo.findAll()
                .stream()
                .filter(p -> p.getKamar().getIdKamar().equals(idKamar))  // Pastikan getIdKamar() ada di kelas Kamar
                .toList();
    }
    
    /**
     * Menghitung total biaya berdasarkan durasi dan harga kamar.
     */
    public float calculateTotalBiaya(Kamar kamar, int durasi) {
        return kamar.getHarga() * durasi;
    }
}
