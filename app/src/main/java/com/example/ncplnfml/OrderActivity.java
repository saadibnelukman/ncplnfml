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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.ncplnfml.CategoryActivity.getOrderPID;
import static com.example.ncplnfml.CategoryActivity.getQtyProducts;
import static com.example.ncplnfml.CategoryActivity.orderPID;
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


//     ArrayList<String> mid = new ArrayList<>();
//     ArrayList<String> date = new ArrayList<>();

     String orderPID, qtyProduct,mid;


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

//        for(int i=0;i<orderProducts.size();i++){
//             orderPID = ordersPID.get(i);
//             qtyProduct = qtyProducts.get(i);
//        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    insertMaster();
                    fetchMaster();
                    insertDetail();



            }
        });











    }

    public void insertMaster(){
        try {
            //Toast.makeText(OrderActivity.this, "qqqq", Toast.LENGTH_SHORT).show();
//                        String orderPID = ordersPID.get(i);
//                        String qtyProduct = qtyProducts.get(i);
            // String insertQuery = "INSERT INTO ORDER_DETAIL(INVENTORY_ITEM_ID,QTY,ORG_ID,ENTRY_BY,ENTRY_DATE)" + "VALUES('" + orderPID + "', " + qtyProduct + ", " + orgId + "," + employeeNumber + ",SYSDATE)";

            initializeConnection();

            String insertQueryMaster = "INSERT INTO ORDER_MASTER(ENTRY_DATE,ENTRY_BY,ORG_ID)" + "VALUES(SYSDATE, " + employeeNumber + ", " + orgId + ")";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQueryMaster);
            preparedStatement.executeQuery();






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

        for(int i = 0; i<ordersPID.size() ; i++){
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
}
