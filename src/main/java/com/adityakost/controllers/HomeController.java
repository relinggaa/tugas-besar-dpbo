package com.adityakost.controllers;

import com.adityakost.entity.CalonPenyewa;
import com.adityakost.entity.Kamar;
import com.adityakost.entity.Pemesanan;
import com.adityakost.entity.Penjaga;
import com.adityakost.repo.CalonPenyewaRepo;
import com.adityakost.repo.PenjagaRepo;
import com.adityakost.service.GambarService;
import com.adityakost.service.KamarService;
import com.adityakost.service.PemesananService;
import com.adityakost.service.PenjagaService;

import jakarta.persistence.IdClass;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private KamarService kamarService;

    @Autowired
    private GambarService gambarService;

    @Autowired
    private CalonPenyewaRepo calonPenyewaRepo;

    @Autowired
    private PemesananService pemesananService;

    @Autowired
    private PenjagaService penjagaService;

    // Menampilkan halaman utama dengan daftar kamar
    @GetMapping
    public String welcome(Model model) {
        List<Kamar> listKamar = kamarService.getAllKamar();
        model.addAttribute("listKamar", listKamar);

        // Menyertakan gambar Base64 untuk setiap kamar
        Map<Long, String> gambarMap = new HashMap<>();
        for (Kamar kamar : listKamar) {
            String gambarBase64 = gambarService.getGambarBase64(kamar.getId());
            if (gambarBase64 != null) {
                gambarMap.put(kamar.getId(), gambarBase64);
            }
        }
        model.addAttribute("gambarMap", gambarMap);

        return "index";
    }

    // Menampilkan halaman login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Proses login
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session, Model model) {
        CalonPenyewa calonPenyewa = calonPenyewaRepo.findByEmailAndPassword(email, password);

        if (calonPenyewa != null) {
            session.setAttribute("user", calonPenyewa);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Email atau password salah");
            return "login";
        }
    }
    @GetMapping("/login-penjaga")
    public String loginPenjaga() {
        return "login-penjaga";
    }


    // Proses login
    @PostMapping("/login-penjaga")
    public String loginPenjaga(@RequestParam("email-penjaga") String emailPenjaga,
                                @RequestParam("password-penjaga") String passwordPenjaga,
                                HttpSession session, Model model) {
        
        Penjaga penjaga = penjagaService.findByEmailPenjagaAndPasswordPenjaga(emailPenjaga, passwordPenjaga);
    
        if (penjaga != null) {
            session.setAttribute("user", penjaga);
            return "/home"; // Redirect ke halaman home jika login berhasil
        } else {
            model.addAttribute("error", "Email atau password salah");
            return "login-penjaga"; 
        }
    }
    

    // Menampilkan halaman register
    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("calonPenyewa", new CalonPenyewa());
        return modelAndView;
    }

    // Proses register
    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute CalonPenyewa calonPenyewa, RedirectAttributes redirectAttributes) {
        if (calonPenyewaRepo.existsByUsername(calonPenyewa.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "Username sudah terdaftar");
            return "redirect:/register";
        }

        if (calonPenyewaRepo.existsByEmail(calonPenyewa.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Email sudah terdaftar");
            return "redirect:/register";
        }

        if (calonPenyewaRepo.existsByPhoneNumber(calonPenyewa.getPhoneNumber())) {
            redirectAttributes.addFlashAttribute("error", "Nomor HP sudah terdaftar");
            return "redirect:/register";
        }

        calonPenyewaRepo.save(calonPenyewa);
        redirectAttributes.addFlashAttribute("success", true); 
        return "redirect:/login"; 
    }

    // Menampilkan halaman pemesanan kamar
    @GetMapping("/pesan")
    public String pesan(@RequestParam("idKamar") Long idKamar, Model model, HttpSession session) {
        // Memeriksa apakah user sudah login
        CalonPenyewa user = (CalonPenyewa) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Kamar kamar = kamarService.getKamarById(idKamar);
        if (kamar == null) {
            return "redirect:/";
        }

        model.addAttribute("kamar", kamar);
        model.addAttribute("user", user);  // Menambahkan user ke model

        return "pesan";
    }

    // Menyimpan pemesanan
    @PostMapping("/pesan")
    public String simpanPemesanan(@RequestParam("idKamar") Long idKamar,
                                  @RequestParam("durasi") int durasi,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes,Model model) {
        CalonPenyewa user = (CalonPenyewa) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Kamar kamar = kamarService.getKamarById(idKamar);
        if (kamar == null) {
            return "redirect:/";
        }

        // Menghitung total biaya
        float totalBiaya = kamar.getHarga() * durasi;

        // Membuat objek pemesanan dan menyimpannya
        Pemesanan pemesanan = new Pemesanan();
        pemesanan.setKamar(kamar);
        pemesanan.setCalonPenyewa(user);
        pemesanan.setDurasi(durasi);
        pemesanan.setTotalBiaya(totalBiaya);

        pemesananService.savePemesanan(pemesanan);

        model.addAttribute("successMessage", "Pemesanan berhasil disimpan!");
        model.addAttribute("kamar", kamar);

    return "pesan";
    }

    // Proses logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("profile")
    public String getProfile() {
        return "profile";
    }
    
    
}
