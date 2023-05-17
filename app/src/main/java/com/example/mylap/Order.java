package com.example.mylap;

public class Order {
    private String name;
    private int numBook;
    private boolean isVip;
    private int totalBill;

    public Order(String name, int numBook, boolean isVip, int totalBill) {
        this.name = name;
        this.numBook = numBook;
        this.isVip = isVip;
        this.totalBill = totalBill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumBook() {
        return numBook;
    }

    public void setNumBook(int numBook) {
        this.numBook = numBook;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public int getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }
}

