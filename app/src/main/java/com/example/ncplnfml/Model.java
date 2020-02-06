package com.example.ncplnfml;

import java.util.ArrayList;

public class Model {

    private boolean isSelected;
    private String product;
    public Model(String product) {
        this.product = product;
    }




    public String getProduct() {
        return product;
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
