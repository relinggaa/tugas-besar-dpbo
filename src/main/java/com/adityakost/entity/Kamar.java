package com.adityakost.entity;

import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name = "tbl_kamar")
public class Kamar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kamar")
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private float harga;

    @OneToOne(mappedBy = "kamar", cascade = CascadeType.ALL)
    private Gambar gambar;
    @OneToMany(mappedBy = "kamar", cascade = CascadeType.ALL)
    private List<Pemesanan> pemesanans;
    // Constructor, getters, and setters

    public Kamar() {}

    public Kamar(String type, float harga) {
        this.type = type;
        this.harga = harga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getHarga() {
        return harga;
    }

    public void setHarga(float harga) {
        this.harga = harga;
    }

    public Gambar getGambar() {
        return gambar;
    }

    public void setGambar(Gambar gambar) {
        this.gambar = gambar;
    }
  
    
}
