package com.example.ncplnfml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

import static com.example.ncplnfml.LoginActivity.createConnection;
import static com.example.ncplnfml.LoginActivity.employeeName;
import static com.example.ncplnfml.LoginActivity.employeeNumber;
import static com.example.ncplnfml.LoginActivity.orgId;

public class HistoryActivity extends AppCompatActivity {

    private Connection connection;
    private boolean connectivity;


    ArrayList<String> mid = new ArrayList<>();
    public static ArrayList<String> qty = new ArrayList<>();
    public static ArrayList<String> desc = new ArrayList<>();


    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    String midQuery,mID,historyQuery;

    MaterialSpinner spinner;
    ArrayAdapter<String> adapter;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.history_RV);
        spinner = (MaterialSpinner) findViewById(R.id.midSpinner);


        initializeConnection();
        getMID();

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,mid);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        getSupportActionBar().setTitle("" +employeeName+ "");


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != -1){

                    mID = spinner.getItemAtPosition(position).toString();
                    historyQuery ="select QTY,DESCRIPTION from ORDER_DETAIL OD,INVENTORY_ITEM II where M_ID = '"+mID+"' AND OD.ENTRY_BY = '"+employeeNumber+"' AND OD.INVENTORY_ITEM_ID = II.INVENTORY_ITEM_ID";
                    try {
                        PreparedStatement preparedStatementHIS = connection.prepareStatement(historyQuery);
                        ResultSet resultSetName = preparedStatementHIS.executeQuery();

                        while (resultSetName.next()) {
                            qty.add (resultSetName.getString(1));
                            desc.add(resultSetName.getString(2));
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    HistoryAdapter historyAdapter = new HistoryAdapter(HistoryActivity.this);
                    recyclerView.setAdapter(historyAdapter);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
//                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(HistoryActivity.this,1);
                    recyclerView.setLayoutManager(gridLayoutManager);





                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        show_Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });


//        historyAdapter = new HistoryAdapter(this);
//        recyclerView.setAdapter(historyAdapter);





    }

    public void getMID(){
        midQuery = "select M_ID from ORDER_MASTER where TO_CHAR(ENTRY_DATE,'dd/mm/yyyy') = TO_CHAR(SYSDATE,'dd/mm/yyyy')  AND ORG_ID = '"+orgId+"' AND ENTRY_BY = '"+employeeNumber+"'";
        try {
            PreparedStatement preparedStatementMID = connection.prepareStatement(midQuery);
            ResultSet resultSetName = preparedStatementMID.executeQuery();

            while (resultSetName.next()) {
                mid.add(resultSetName.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

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
