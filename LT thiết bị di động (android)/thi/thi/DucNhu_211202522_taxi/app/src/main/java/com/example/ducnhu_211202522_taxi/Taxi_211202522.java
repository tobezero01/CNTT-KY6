package com.example.ducnhu_211202522_taxi;

public class Taxi_211202522 {
    private int id;
    private String soXe;
    private double quangDuong;
    private int donGia;
    private int km;

    public Taxi_211202522() {
    }

    public Taxi_211202522(String soXe, double quangDuong, int donGia, int km) {
        this.soXe = soXe;
        this.quangDuong = quangDuong;
        this.donGia = donGia;
        this.km = km;
    }

    public Taxi_211202522(int id, String soXe, double quangDuong, int donGia, int km) {
        this.id = id;
        this.soXe = soXe;
        this.quangDuong = quangDuong;
        this.donGia = donGia;
        this.km = km;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSoXe(String soXe) {
        this.soXe = soXe;
    }

    public void setQuangDuong(double quangDuong) {
        this.quangDuong = quangDuong;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public int getId() {
        return id;
    }

    public String getSoXe() {
        return soXe;
    }

    public double getQuangDuong() {
        return quangDuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public int getKm() {
        return km;
    }
}
