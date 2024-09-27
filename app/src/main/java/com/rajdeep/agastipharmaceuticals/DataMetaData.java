package com.rajdeep.agastipharmaceuticals;

import java.io.Serializable;

public class DataMetaData implements Serializable {

    int position;
    String quantity;
    String medicineName;
    String product_id;
    String price;

    public DataMetaData(int position, String quantity, String medicineName, String product_id, String price){
        this.position = position;
        this.quantity = quantity;
        this.medicineName = medicineName;
        this.product_id = product_id;
        this.price = price;

    }
}
