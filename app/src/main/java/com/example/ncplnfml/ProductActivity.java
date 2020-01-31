package com.example.ncplnfml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.ncplnfml.LoginActivity.createConnection;

public class ProductActivity extends AppCompatActivity {

    private Connection connection;
    private boolean connectivity;

    RecyclerView recyclerView;
    ListView listView;

    private String productQuery;
    private String category = "";



    CheckBox checkBox;


    private static ArrayList<String>product = new ArrayList<>();

    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerView = findViewById(R.id.product_RV);


        if(getIntent().hasExtra("category")){
            category = getIntent().getStringExtra("category");

        }

        productQuery ="select DESCRIPTION from INVENTORY_ITEM where ITEM_CATEGORY = '"+ category +"'";

        //String p;

        initializeConnection();
        product.clear();
        try {
            PreparedStatement preparedStatementProduct = connection.prepareStatement(productQuery);
            ResultSet resultSetProduct = preparedStatementProduct.executeQuery();

            while (resultSetProduct.next()) {
                product.add(resultSetProduct.getString(1));


            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        productAdapter = new ProductAdapter(this);
        recyclerView.setAdapter(productAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);



//        productAdapter.setOnItemClickListenerProduct(new ProductAdapter.ClickListenerProduct() {
//            @Override
//            public void onItemClick(int position, View v) {
//
//
//                Intent intent = new Intent(ProductActivity.this,OrderActivity.class);
//                intent.putExtra("product",product.get(position));
//                startActivity(intent);
//            }
//        });


    }

//    private ArrayList<Model> getModel(boolean isSelect){
//        ArrayList<Model> list = new ArrayList<>();
//        for(int i = 0; i < 4; i++){
//
//            Model model = new Model();
//            model.setSelected(isSelect);
//            model.setProduct(animallist[i]);
//            list.add(model);
//        }
//        return list;
//    }

    public static ArrayList<String> getProduct(){
        return product;
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
