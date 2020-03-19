package com.example.ncplnfml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import static com.example.ncplnfml.CategoryActivity.getOrderPID;
import static com.example.ncplnfml.CategoryActivity.getOrderProduct;
import static com.example.ncplnfml.CategoryActivity.getQtyProducts;
import static com.example.ncplnfml.LoginActivity.createConnection;
import static com.example.ncplnfml.LoginActivity.customer_id;
import static com.example.ncplnfml.LoginActivity.orgId;
import static com.example.ncplnfml.LoginActivity.user_type;

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
    public static ArrayList<String>ava_qty = new ArrayList<>();
    public static Model[] model;
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

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        recyclerView = findViewById(R.id.product_RV);


        ibtn = (Button) findViewById(R.id.iBtn);
        dbtn = findViewById(R.id.dBtn);

        qty = findViewById(R.id.qty);


        if(getIntent().hasExtra("category")){
            category = getIntent().getStringExtra("category");

        }

        //productQuery ="select DESCRIPTION, INVENTORY_ITEM_ID from INVENTORY_ITEM where ITEM_CATEGORY = '"+ category +"' AND ORG_ID ='"+LoginActivity.org+"'";
        productQuery ="select II.DESCRIPTION, II.INVENTORY_ITEM_ID,IT.AVA_QTY from INVENTORY_ITEM II,ITEM_STOCK IT where II.ITEM_CATEGORY = '"+ category +"' AND II.ORG_ID ='"+ LoginActivity.org +"' AND II.INVENTORY_ITEM_ID = IT.INVENTORY_ITEM_ID AND IT.CUSTOMER_ID = '"+customer_id+"'";



        initializeConnection();
        product.clear();
        pid.clear();
        try {
            PreparedStatement preparedStatementProduct = connection.prepareStatement(productQuery);
            ResultSet resultSetProduct = preparedStatementProduct.executeQuery();

            while (resultSetProduct.next()) {
                product.add(resultSetProduct.getString(1));
                pid.add(resultSetProduct.getString(2));
                ava_qty.add(resultSetProduct.getString(3));



            }
            model = new Model[product.size()];
            for(int i=0; i< model.length;i++) {
                model[i] = new Model(product.get(i),pid.get(i),ava_qty.get(i));
            }
            Toast.makeText(ProductActivity.this,model.length+"", Toast.LENGTH_SHORT).show();


            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        productAdapter = new ProductAdapter(this,model);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
//        recyclerView.setLayoutManager(gridLayoutManager);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout,menu);
//        invalidateOptionsMenu();


        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logout);
        item.setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.cart){
            Intent intent = new Intent(ProductActivity.this,OrderActivity.class);
//            intent.putExtra("product",orderProducts);
//            intent.putExtra("qty",qtyProducts);
//            intent.putExtra("pid",orderPID);
            startActivity(intent);

        }
        if(item.getItemId() == R.id.history){

            if(user_type.equals("1")){
                item.setTitle("Orders History");
            }else{
                item.setTitle("Sales History");
            }

            Intent intent = new Intent(ProductActivity.this,HistoryActivity.class);
            startActivity(intent);
        }
//        if(item.getItemId() == R.id.logout){
//
//            logout();
//
//            return  true;
//        }
//        if(item.getItemId() == R.id.emp_info){
//
//           item.setTitle(""+employeeName);
//        }


        return super.onOptionsItemSelected(item);
    }
//    private void logout() {
//        final AlertDialog.Builder alert = new AlertDialog.Builder(ProductActivity.this);
//        alert.setTitle("Confirmation");
//        alert.setMessage("Do you want to logout?");
//        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//                getOrderProduct().clear();
//                getOrderPID().clear();
//                getQtyProducts().clear();
//                finish();
//            }
//        });
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
