package com.rajdeep.agastipharmaceuticals;

public class MyOrderMetaData {
    String amount;
    String status;
    String date;
    String sale_id;
    String position;

    public MyOrderMetaData(String amount, String status, String date, String sale_id, String position){
        this.amount = amount;
        this.status = status;
        this.date = date;
        this.sale_id = sale_id;
        this.position = position;

    }

    public MyOrderMetaData() {

    }
}
