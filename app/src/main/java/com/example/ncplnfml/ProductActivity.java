package com.example.ncplnfml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Array;
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
    FloatingActionButton fbtn;
    ListView listView;

    private String productQuery;
    private String category = "";



    CheckBox checkBox;


    private static ArrayList<String>product = new ArrayList<>();
    private static Model[] model;
    //public static Model[] model;

    ProductAdapter productAdapter;
    StringBuilder sb=null;

     static ArrayList<String> orderProducts;
    //ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        recyclerView = findViewById(R.id.product_RV);

        fbtn = findViewById(R.id.AddBtn);


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

                //Model[] model = new Model(resultSetProduct.getString(i));

            }
            model = new Model[product.size()];
            for(int i=0; i< model.length;i++) {
                model[i] = new Model(product.get(i));
            }
            Toast.makeText(ProductActivity.this,model.length+"", Toast.LENGTH_SHORT).show();


            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }



       // productAdapter = new ProductAdapter(this,getProducts());
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sb=new StringBuilder();

                orderProducts = new ArrayList<>();

                int i=0;
                do {
                    Model product=productAdapter.checkedProducts.get(i);

                    orderProducts.add(product.getProduct());

                    sb.append(product.getProduct());


                    if(i != productAdapter.checkedProducts.size()-1){
                        sb.append("n");
                    }
                    i++;

                }while (i < productAdapter.checkedProducts.size());


                Intent intent = new Intent(ProductActivity.this,OrderActivity.class);
//                intent.putExtra("product",orderProducts);
                intent.putExtra("product",orderProducts);
                startActivity(intent);

                if(productAdapter.checkedProducts.size()>0)
                {
                    Toast.makeText(ProductActivity.this,orderProducts.size()+"Items Added"+"",Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(ProductActivity.this,"Please Check An Item First", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //Model[] products = product.toArray();

        productAdapter = new ProductAdapter(this,model);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
//        recyclerView.setLayoutManager(gridLayoutManager);



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







    public Model[] getProduct(){
        for(int i=0 ;i< model.length;i++) {

                    model[i].setProduct(product.get(i));
                    //Model[] model = new Model(resultSetProduct.getString(i));

                    Toast.makeText(this,"do", Toast.LENGTH_SHORT).show();
                         }

        return model;
    }

    public static ArrayList<String> getOrderProduct(){
        return orderProducts;
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
