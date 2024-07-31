package com.example.nhudinhduc_211202522;

public class Contact_NhuDinhDuc {
    private int id;
    private String ten;
    private String soDienThoai;

    public Contact_NhuDinhDuc() {
    }

    public Contact_NhuDinhDuc(int id, String ten, String soDienThoai) {
        this.id = id;
        this.ten = ten;
        this.soDienThoai = soDienThoai;
    }

    public Contact_NhuDinhDuc(String ten, String soDienThoai) {

        this.ten = ten;
        this.soDienThoai = soDienThoai;
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

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
}
