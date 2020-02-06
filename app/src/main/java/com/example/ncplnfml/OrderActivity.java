package com.example.ncplnfml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.example.ncplnfml.LoginActivity.createConnection;

public class OrderActivity extends AppCompatActivity {


    private Connection connection;
    private boolean connectivity;

    RecyclerView recyclerView;
    TextView product_name;
    //FloatingActionButton btnCart;
    //ElegantNumberButton numberButton;
    //private String productName = "";
    ArrayList<String> orderProducts = new ArrayList<>();

   // OrderAdapter orderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        //category = getIntent().getParcelableArrayExtra("category");
        initializeConnection();
        product_name = (TextView) findViewById(R.id.product_name);
        recyclerView = findViewById(R.id.orderRV);
        //unit_price = (TextView) findViewById(R.id.unit_price);
        //btnCart = findViewById(R.id.btnCart);
        //numberButton = findViewById(R.id.numberButton);

        if(getIntent().hasExtra("product")){
            orderProducts = getIntent().getStringArrayListExtra("product");

        OrderAdapter orderAdapter = new OrderAdapter(this,orderProducts);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);


        }








    }

    public void initializeConnection(){
        try {
            this.connection = createConnection();
            //statement = connection.createStatement();
            connectivity = true;
            // Toast.makeText(this, "Connected.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            connectivity = false;
            Toast.makeText(this, "Connection error check internet connection and try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
