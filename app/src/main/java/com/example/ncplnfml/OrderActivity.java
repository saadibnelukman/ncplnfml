package com.example.ncplnfml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.ncplnfml.CategoryActivity.getOrderPID;
import static com.example.ncplnfml.CategoryActivity.getOrderProduct;
import static com.example.ncplnfml.CategoryActivity.getQtyProducts;
import static com.example.ncplnfml.CategoryActivity.orderPID;
import static com.example.ncplnfml.LoginActivity.createConnection;
import static com.example.ncplnfml.LoginActivity.customer_id;
import static com.example.ncplnfml.LoginActivity.employeeName;
import static com.example.ncplnfml.LoginActivity.employeeNumber;
import static com.example.ncplnfml.LoginActivity.orgId;

public class OrderActivity extends AppCompatActivity {


        private Connection connection;
        private boolean connectivity;

        RecyclerView recyclerView;
        TextView product_name;
        EditText qty;

        PreparedStatement preparedStatement;

        Button submitBtn;

        ArrayList<String> orderProducts = new ArrayList<>();
        ArrayList<String> qtyProducts = new ArrayList<>();
        ArrayList<String> ordersPID = new ArrayList<>();

        String orderPID, qtyProduct,mid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        getSupportActionBar().setTitle("Order Details");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        initializeConnection();
        product_name = (TextView) findViewById(R.id.product_name);
        recyclerView = findViewById(R.id.orderRV);
        submitBtn = findViewById(R.id.submitBtn);


        if(getIntent().hasExtra("product")){
            orderProducts = getIntent().getStringArrayListExtra("product");

        }
        if(getIntent().hasExtra("qty")){

            qtyProducts = getIntent().getStringArrayListExtra("qty");

        }

        if(getIntent().hasExtra("pid")){

            ordersPID = getIntent().getStringArrayListExtra("pid");
        }


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context;
                final AlertDialog.Builder alert = new AlertDialog.Builder(OrderActivity.this);
                alert.setTitle("Confirmation");
                alert.setMessage("Do you want to submit?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        insertMaster();
                        fetchMaster();
                        insertDetail();
                        getQtyProducts().clear();
                        getOrderProduct().clear();

                        Intent intent = new Intent(OrderActivity.this,CategoryActivity.class);
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.create().show();






            }
        });

        OrderAdapter orderAdapter = new OrderAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);

    }

    public void insertMaster(){
        try {

            initializeConnection();

            String insertQueryMaster = "INSERT INTO ORDER_MASTER(ENTRY_DATE,ENTRY_BY,ORG_ID,CUSTOMER_ID)" + "VALUES(SYSDATE, " + employeeNumber + ", " + LoginActivity.org + "," + customer_id + ")";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQueryMaster);
            preparedStatement.executeQuery();




            //showAlertBox("Confirmation","Done");

          Toast.makeText(OrderActivity.this, "Done", Toast.LENGTH_SHORT).show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fetchMaster(){

        try {
            String fetchQuery = "SELECT MAX(M_ID) FROM ORDER_MASTER WHERE ENTRY_BY = '"+employeeNumber+"'";
            PreparedStatement preparedStatementFetch = connection.prepareStatement(fetchQuery);
            ResultSet resultSetFetch = preparedStatementFetch.executeQuery();

            while (resultSetFetch.next()) {
                mid = (resultSetFetch.getString(1));
                //date.add(resultSetFetch.getString(2));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertDetail(){

        ordersPID = getOrderPID();
        qtyProducts = getQtyProducts();

        for(int i = 0; i<qtyProducts.size() ; i++){
            try {

                orderPID = ordersPID.get(i);
                qtyProduct = qtyProducts.get(i);

                String insertQuery = "INSERT INTO ORDER_DETAIL(M_ID,INVENTORY_ITEM_ID,QTY,ORG_ID,ENTRY_BY,ENTRY_DATE)" + "VALUES('" + mid + "','" + orderPID + "', '"+qtyProduct+"','"+LoginActivity.org+"','"+employeeNumber+"',SYSDATE)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.executeQuery();


            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private void showAlertBox(String title, String msg) {
        new AlertDialog.Builder(OrderActivity.this)
                .setTitle(title)
                .setMessage(msg)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
//                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        // Continue with delete operation
//                                    }
//                                })
                .setPositiveButton(android.R.string.yes, null)

                // A null listener allows the button to dismiss the dialog and take no further action.
                //.setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
