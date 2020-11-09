package com.rizkirian.rsantrian.antrian;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class Antrian {
    private String id_pasien;
    private String nama_pasien;
    private String nomor_antrian;
    private String di_layani;

    public String getDi_layani() {
        return di_layani;
    }

    public void setDi_layani(String di_layani) {
        this.di_layani = di_layani;
    }

    public String getId_pasien() {
        return id_pasien;
    }

    public void setId_pasien(String id_pasien) {
        this.id_pasien = id_pasien;
    }

    public String getNama_pasien() {
        return nama_pasien;
    }

    public void setNama_pasien(String nama_pasien) {
        this.nama_pasien = nama_pasien;
    }

    public String getNomor_antrian() {
        return nomor_antrian;
    }

    public void setNomor_antrian(String nomor_antrian) {
        this.nomor_antrian = nomor_antrian;
    }
}
