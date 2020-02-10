package com.example.ncplnfml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.ncplnfml.LoginActivity.createConnection;
import static com.example.ncplnfml.LoginActivity.employeeNumber;
import static com.example.ncplnfml.LoginActivity.orgId;

public class OrderActivity extends AppCompatActivity {


    private Connection connection;
    private boolean connectivity;

    RecyclerView recyclerView;
    TextView product_name;

    PreparedStatement preparedStatement;

    Button submitBtn;
    //FloatingActionButton btnCart;
    //ElegantNumberButton numberButton;
    //private String productName = "";
    ArrayList<String> orderProducts = new ArrayList<>();
     ArrayList<String> qtyProducts = new ArrayList<>();
     ArrayList<String> ordersPID = new ArrayList<>();

   // OrderAdapter orderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        //category = getIntent().getParcelableArrayExtra("category");
        initializeConnection();
        product_name = (TextView) findViewById(R.id.product_name);
        recyclerView = findViewById(R.id.orderRV);
        submitBtn = findViewById(R.id.submitBtn);
        //unit_price = (TextView) findViewById(R.id.unit_price);
        //btnCart = findViewById(R.id.btnCart);
        //numberButton = findViewById(R.id.numberButton);

        if(getIntent().hasExtra("product")){
            orderProducts = getIntent().getStringArrayListExtra("product");

        OrderAdapter orderAdapter = new OrderAdapter(this,orderProducts);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);
        }
        if(getIntent().hasExtra("qty")){

            qtyProducts = getIntent().getStringArrayListExtra("qty");
            OrderAdapter orderAdapter = new OrderAdapter(this,qtyProducts);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(orderAdapter);
        }

        if(getIntent().hasExtra("pid")){

            ordersPID = getIntent().getStringArrayListExtra("pid");
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<orderProducts.size();i++) {
                    try {
                        //Toast.makeText(OrderActivity.this, "qqqq", Toast.LENGTH_SHORT).show();
                        String orderPID = ordersPID.get(i);
                        String qtyProduct = qtyProducts.get(i);
                        String insertQuery = "INSERT INTO ORDER_DETAIL(INVENTORY_ITEM_ID,QTY,ORG_ID,ENTRY_BY,ENTRY_DATE)" + "VALUES('" + orderPID + "', " + qtyProduct + ", " + orgId + "," + employeeNumber + ",SYSDATE)";

                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.executeQuery();
                        Toast.makeText(OrderActivity.this, "Done", Toast.LENGTH_SHORT).show();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }


            }
        });











    }

    public void initializeConnection(){
        try {
            this.connection = createConnection();
            //statement = connection.createStatement();
            connectivity = true;
             //Toast.makeText(this, "Connected.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            connectivity = false;
            Toast.makeText(this, "Connection error check internet connection and try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
