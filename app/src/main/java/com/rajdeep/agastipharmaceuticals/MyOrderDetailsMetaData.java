package com.rajdeep.agastipharmaceuticals;

public class MyOrderDetailsMetaData {
    String medicineName;
    String rate;
    String quantity;
    String payableAmount;
    String discount;
    String total;

    public MyOrderDetailsMetaData(String medicineName, String rate, String quantity, String payableAmount, String discount, String total){
        this.medicineName = medicineName;
        this.rate = rate;
        this.quantity = quantity;
        this.payableAmount = payableAmount;
        this.discount = discount;
        this.total = total;

    }

    public MyOrderDetailsMetaData() {

    }
}
