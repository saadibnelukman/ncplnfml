package com.example.ncplnfml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.ncplnfml.LoginActivity.createConnection;

public class CategoryActivity extends AppCompatActivity {


    private Connection connection;
    private boolean connectivity;
    //String [] title;

    CatergoryAdaptor catergoryAdaptor;
   // private ArrayList<String> category = new ArrayList<>();
    private static ArrayList<String> category = new ArrayList<>();
    static ArrayList<String> orderProducts = new ArrayList<>();
    static ArrayList<String> qtyProducts = new ArrayList<>();
    static ArrayList<String> orderPID = new ArrayList<>();

    RecyclerView recyclerView;
    private String categoryQuery;
   // String org;
    //String category;

    FloatingActionButton cartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerView = findViewById(R.id.recyclerView);
        cartBtn = findViewById(R.id.btnCart);

        //org = getIntent().getStringExtra("ORG_ID");

        categoryQuery = "select DISTINCT(ITEM_CATEGORY) from INVENTORY_ITEM";
        initializeConnection();
        category.clear();
        try {
            PreparedStatement preparedStatementName = connection.prepareStatement(categoryQuery);
            ResultSet resultSetName = preparedStatementName.executeQuery();

            while (resultSetName.next()) {
                category.add(resultSetName.getString(1));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        catergoryAdaptor = new CatergoryAdaptor(this);

        recyclerView.setAdapter(catergoryAdaptor);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        catergoryAdaptor.setOnItemClickListener(new CatergoryAdaptor.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                //Toast.makeText(getApplicationContext(), "yeah.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CategoryActivity.this,ProductActivity.class);
                //intent.putParcelableArrayListExtra("category",category);
                intent.putExtra("category",category.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });



        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,OrderActivity.class);
                intent.putExtra("product",orderProducts);
                intent.putExtra("qty",qtyProducts);
                intent.putExtra("pid",orderPID);
                startActivity(intent);
            }
        });

    }

    public static ArrayList<String>getList(){
        return category;
    }

    public static void addToArray(String s,String p){
        orderProducts.add(s);
        orderPID.add(p);
    }
    public static void addQty(int position, String q){
        qtyProducts.add(q);
        Log.d("Array", q+"--"+position);
//        Toast.makeText(CategoryActivity.this,q+"--"+ position,Toast.LENGTH_SHORT).show();
    }
    public static ArrayList<String> getOrderProduct(){
        return orderProducts;
    }
    public static ArrayList<String>getQtyProducts(){
        return qtyProducts;
    }
    public static ArrayList<String>getOrderPID(){
        return orderPID;
    }
    public void initializeConnection(){
        try {
            this.connection = createConnection();
            //statement = connection.createStatement();
            connectivity = true;
            Toast.makeText(this, "Connected.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            connectivity = false;
            Toast.makeText(this, "Connection error check internet connection and try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
