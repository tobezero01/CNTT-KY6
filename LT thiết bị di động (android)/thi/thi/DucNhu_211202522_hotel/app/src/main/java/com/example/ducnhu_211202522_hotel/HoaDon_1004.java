package com.example.ducnhu_211202522_hotel;

public class HoaDon_1004 {
    private int id;
    private String customerName;
    private int roomNumber;
    private double unitPrice;
    private int numberOfDays;

    public HoaDon_1004() {
    }

    public HoaDon_1004(int id, String customerName, int roomNumber, double unitPrice, int numberOfDays) {
        this.id = id;
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.unitPrice = unitPrice;
        this.numberOfDays = numberOfDays;
    }

    // Constructor
    public HoaDon_1004(String customerName, int roomNumber, double unitPrice, int numberOfDays) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.unitPrice = unitPrice;
        this.numberOfDays = numberOfDays;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }


    public double setTotalPrice() {
        return unitPrice * numberOfDays;
    }
}
