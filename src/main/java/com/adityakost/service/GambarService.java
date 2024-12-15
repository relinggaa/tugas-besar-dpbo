package com.adityakost.service;

import com.adityakost.entity.Gambar;
import com.adityakost.entity.Kamar;
import com.adityakost.repo.GambarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class GambarService {

    @Autowired
    private GambarRepo gambarRepo;

    public Gambar getGambarByKamarId(Long kamarId) {
        return gambarRepo.findByKamarId(kamarId);
    }

    public String getGambarBase64(Long kamarId) {
        Gambar gambar = gambarRepo.findByKamarId(kamarId);
        return gambar != null ? Base64.getEncoder().encodeToString(gambar.getDataGambar()) : null;
    }

    public void saveGambarWithKamar(Kamar kamar, MultipartFile gambarFile) throws IOException {
        String fileType = gambarFile.getContentType();
    
        if (fileType == null || !fileType.startsWith("image")) {
            throw new IllegalArgumentException("Hanya file gambar yang diizinkan.");
        }
    
        Gambar gambar = new Gambar();
        gambar.setNama(gambarFile.getOriginalFilename());
        gambar.setDataGambar(gambarFile.getBytes());
        gambar.setKamar(kamar);
    
        gambarRepo.save(gambar);
    }
    
    

    


  
}




