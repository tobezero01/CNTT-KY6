package com.example.nhuduc_211202522;

public class GiaoDich {
    private int id;
    private String ten;
    private String tien;
    private String nd;
    private String date;
    private boolean loai;

    public GiaoDich() {
    }

    public GiaoDich(String ten, String tien, String nd, String date, boolean loai) {
        this.ten = ten;
        this.tien = tien;
        this.nd = nd;
        this.date = date;
        this.loai = loai;
    }

    public GiaoDich(int id, String ten, String tien, String nd, String date, boolean loai) {
        this.id = id;
        this.ten = ten;
        this.tien = tien;
        this.nd = nd;
        this.date = date;
        this.loai = loai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTien() {
        return tien;
    }

    public void setTien(String tien) {
        this.tien = tien;
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isLoai() {
        return loai;
    }

    public void setLoai(boolean loai) {
        this.loai = loai;
    }
}
