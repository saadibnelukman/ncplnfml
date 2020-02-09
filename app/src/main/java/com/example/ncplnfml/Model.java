package com.example.ncplnfml;

import java.util.ArrayList;

public class Model {

    private boolean isSelected;
    private String product;
    private String PID;
    public Model(String product,String PID) {
        this.product = product;
        this.PID = PID;
    }




    public String getProduct() {
        return product;
    }

    public String getPID() {
        return PID;
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
