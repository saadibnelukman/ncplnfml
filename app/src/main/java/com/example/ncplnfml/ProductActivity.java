package com.example.ncplnfml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.ncplnfml.CategoryActivity.addToArray;
import static com.example.ncplnfml.LoginActivity.createConnection;
import static com.example.ncplnfml.LoginActivity.orgId;

public class ProductActivity extends AppCompatActivity {

    private Connection connection;
    private boolean connectivity;

    RecyclerView recyclerView;
    FloatingActionButton fbtn;
    //ElegantNumberButton qtyBtn;
    ListView listView;

    private String productQuery;
    private String category = "";

    private int counter;

    public static Button ibtn,dbtn;

    CheckBox checkBox;


    private static ArrayList<String>product = new ArrayList<>();
    private static ArrayList<String>pid = new ArrayList<>();
    private static Model[] model;
    //public static Model[] model;

    static ProductAdapter productAdapter;
    StringBuilder sb=null;
    public static EditText qty;
//    Button iBtn,dBtn;


     static ArrayList<String> orderProducts;
    //ProductAdapter adapter;
   // String qty ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        recyclerView = findViewById(R.id.product_RV);


        ibtn = (Button) findViewById(R.id.iBtn);
        dbtn = findViewById(R.id.dBtn);

        qty = findViewById(R.id.qty);


        if(getIntent().hasExtra("category")){
            category = getIntent().getStringExtra("category");

        }

        productQuery ="select DESCRIPTION, INVENTORY_ITEM_ID from INVENTORY_ITEM where ITEM_CATEGORY = '"+ category +"' AND ORG_ID ='"+LoginActivity.org+"'";



        initializeConnection();
        product.clear();
        pid.clear();
        try {
            PreparedStatement preparedStatementProduct = connection.prepareStatement(productQuery);
            ResultSet resultSetProduct = preparedStatementProduct.executeQuery();

            while (resultSetProduct.next()) {
                product.add(resultSetProduct.getString(1));
                pid.add(resultSetProduct.getString(2));



            }
            model = new Model[product.size()];
            for(int i=0; i< model.length;i++) {
                model[i] = new Model(product.get(i),pid.get(i));
            }
            Toast.makeText(ProductActivity.this,model.length+"", Toast.LENGTH_SHORT).show();


            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }



//        fbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                int i=0;
//                do {
//                    Model product=productAdapter.checkedProducts.get(i);
//                    addToArray(product.getProduct(),product.getPID());
//                    i++;
//
//                }while (i < productAdapter.checkedProducts.size());
//
//
//                if(productAdapter.checkedProducts.size()>0)
//                {
//                    Toast.makeText(ProductActivity.this,"Items Added"+"",Toast.LENGTH_LONG).show();
//                }else
//                {
//                    Toast.makeText(ProductActivity.this,"Please Check An Item First", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });






        productAdapter = new ProductAdapter(this,model);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
//        recyclerView.setLayoutManager(gridLayoutManager);

    }

//    public static void setOff(){
//        ibtn.setEnabled(false);
//        dbtn.setEnabled(false);
//        qty.setEnabled(false);
//    }

//    public static void  addModel(){
//        int i=0;
//        do {
//            Model product=productAdapter.checkedProducts.get(i);
//            addToArray(product.getProduct(),product.getPID());
//            i++;
//
//        }while (i < productAdapter.checkedProducts.size());


//        if(productAdapter.checkedProducts.size()>0)
//        {
//            Toast.makeText(ProductActivity.this,"Items Added"+"",Toast.LENGTH_LONG).show();
//        }else
//        {
//            Toast.makeText(ProductActivity.this,"Please Check An Item First", Toast.LENGTH_SHORT).show();
//        }
//    }


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
