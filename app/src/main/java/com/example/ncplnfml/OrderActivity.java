package com.example.ncplnfml;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    TextView product_name,unit_price;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;
    private String productName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        //category = getIntent().getParcelableArrayExtra("category");

        product_name = (TextView) findViewById(R.id.product_name);
        unit_price = (TextView) findViewById(R.id.unit_price);
        btnCart = findViewById(R.id.btnCart);
        numberButton = findViewById(R.id.numberButton);

        if(getIntent().hasExtra("product")){
            productName = getIntent().getStringExtra("product");
            product_name.setText(productName);
        }








    }
}
