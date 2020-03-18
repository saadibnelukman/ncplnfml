package com.example.ncplnfml;

import java.util.ArrayList;

public class Model {

    private boolean isSelected;
    private String product;
    private String PID;
    private String ava_qty;
    public Model(String product,String PID,String ava_qty) {
        this.product = product;
        this.PID = PID;
        this.ava_qty = ava_qty;
    }




    public String getProduct() {
        return product;
    }

    public String getPID() {
        return PID;
    }
    public String getAvaQty() {
        return ava_qty;
    }
    public void setProduct(String product) {
        this.product = product;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }





}
