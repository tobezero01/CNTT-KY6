package com.example.ducnhu_211202522_thisinh;

public class ThiSinh {
    private String id;
    private String hoTen;
    private double diemToan;
    private double diemLy;
    private double diemHoa;

    public ThiSinh(String id, String hoTen, double diemToan, double diemLy, double diemHoa) {
        this.id = id;
        this.hoTen = hoTen;
        this.diemToan = diemToan;
        this.diemLy = diemLy;
        this.diemHoa = diemHoa;
    }

    public ThiSinh(String hoTen, double diemToan, double diemLy, double diemHoa) {
        this.hoTen = hoTen;
        this.diemToan = diemToan;
        this.diemLy = diemLy;
        this.diemHoa = diemHoa;
    }

    // Constructor

    // Getter và Setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public double getDiemToan() {
        return diemToan;
    }

    public void setDiemToan(double diemToan) {
        this.diemToan = diemToan;
    }

    public double getDiemLy() {
        return diemLy;
    }

    public void setDiemLy(double diemLy) {
        this.diemLy = diemLy;
    }

    public double getDiemHoa() {
        return diemHoa;
    }

    public void setDiemHoa(double diemHoa) {
        this.diemHoa = diemHoa;
    }


    public double tongDiem() {
        return this.diemToan + this.diemLy + this.diemHoa;
    }

    public double diemTrungBinh() {
        return tongDiem() / 3;
    }
    @Override
    public String toString() {
        return "Số báo danh: " + id + "\n" +
                "Họ tên: " + hoTen + "\n" +
                "Điểm Toán: " + diemToan + "\n" +
                "Điểm Lý: " + diemLy + "\n" +
                "Điểm Hóa: " + diemHoa;
    }
}
