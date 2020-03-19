package com.example.ncplnfml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;

import static com.example.ncplnfml.LoginActivity.createConnection;
import static com.example.ncplnfml.LoginActivity.employeeName;
import static com.example.ncplnfml.LoginActivity.employeeNumber;
import static com.example.ncplnfml.LoginActivity.orgId;
import static com.example.ncplnfml.LoginActivity.user_type;

public class HistoryActivity extends AppCompatActivity {

    private Connection connection;
    private boolean connectivity;


    ArrayList<String> mid = new ArrayList<>();
    ArrayList<String> order_date = new ArrayList<>();
    ArrayList<String> damage_stat = new ArrayList<>();
    public static ArrayList<String> qty = new ArrayList<>();
    public static ArrayList<String> desc = new ArrayList<>();
    ArrayList<String> concat_order = new ArrayList<>();
    DatePickerDialog.OnDateSetListener onDateSetListener;
    public static String currentDate;

    Button show_btn;
    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    String midQuery, mID, historyQuery,orderDate;
    static TextView from_date_TV, to_date_TV;
    //Button from_date_Btn;

    //----------------------------------------------------------DATE PICKER 1
    public static String date1;
    public static final String DATE_DIALOG_1 = "datePicker1";
    static Button from_date_Btn;
    private static int mYear1;
    private static int mMonth1;
    private static int mDay1;
    //----------------------------------------------------------DATE PICKER 1


    //----------------------------------------------------------DATE PICKER 2
    public static String date2;
    public static final String DATE_DIALOG_2 = "datePicker2";
    static Button to_date_Btn;
    private static int mYear2;
    private static int mMonth2;
    private static int mDay2;
    //----------------------------------------------------------DATE PICKER 2


    MaterialSpinner spinner;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.history_RV);
        spinner = findViewById(R.id.midSpinner);
        from_date_TV = findViewById(R.id.from_date_TV);
        from_date_Btn = findViewById(R.id.from_date);
        to_date_TV = findViewById(R.id.to_date_TV);
        to_date_Btn = findViewById(R.id.to_date);
        show_btn = findViewById(R.id.show_btn);

        if (user_type.equals("1")) {
            getSupportActionBar().setTitle("Order History");
        } else {
            getSupportActionBar().setTitle("Sales History");
        }

        //----------------------------------------------------------DATE PICKER 1
        //txt_date1 = findViewById(R.id.txt_date1);
        from_date_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DialogFragment newFragment1 = new DatePickerFragment1();
                newFragment1.show(getSupportFragmentManager(), DATE_DIALOG_1);
            }
        });
        //----------------------------------------------------------DATE PICKER 1


        //----------------------------------------------------------DATE PICKER 2
        //txt_date2 = findViewById(R.id.txt_date2);
        to_date_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DialogFragment newFragment2 = new DatePickerFragment2();
                newFragment2.show(getSupportFragmentManager(), DATE_DIALOG_2);
            }
        });
        //----------------------------------------------------------DATE PICKER 2//
        initializeConnection();

        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMID();
                //spinner.performClick();
               // Log.d("mid",mid.get(1));

                int length = mid.size();
                if (length != order_date.size()) {

                }
                concat_order.clear();
                for (int i = 0; i < length; i++) {
                    String damage = damage_stat.get(i);
                    if(damage.equals("0")) {
                        concat_order.add(order_date.get(i) + " SL " + mid.get(i));
                    }else{
                        concat_order.add(order_date.get(i) + " SL " + mid.get(i) + " " + "**");
                    }
                    Log.d("mid",concat_order.get(i));
                }
                spinner.setSelection(1);

            }
        });

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,concat_order);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != -1){

                    qty.clear();
                    desc.clear();

                   // mID = spinner.getItemAtPosition(position).toString();
                    int pos = spinner.getSelectedItemPosition();
                    Log.d("pos",String.valueOf(pos));
                    mID = mid.get(pos-1);
                    Log.d("m",""+mID);
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

                }else{
                    qty.clear();
                    desc.clear();
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


        historyAdapter = new HistoryAdapter(this);
        recyclerView.setAdapter(historyAdapter);



    }

    //----------------------------------------------------------DATE PICKER 1
    public static class DatePickerFragment1 extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // set default date

            //Date Time FROM BEFORE
//            String date =  txt_date1.getText().toString().trim();
//            String[] data = date.split("-", 3);
//            int year = Integer.parseInt(data[0]);
//            int month = Integer.parseInt(data[1])-1;    //Because January is 0
//            int day = Integer.parseInt(data[2]);

            //Date Time NOW
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), R.style.DatePicker1, this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // get selected date
            mYear1 = year;
            mMonth1 = month;
            mDay1 = day;
            // show selected date to date button
            date1 = mMonth1 + 1+"/"+mDay1+"/"+mYear1;
            from_date_TV.setText(date1);
//            from_date_TV.setText(new StringBuilder()
//                    .append(mMonth1 + 1).append("/")
//                    .append(mDay1).append("/")
//                    .append(mYear1).append(""));
        }
    }
    //----------------------------------------------------------DATE PICKER 1


    //----------------------------------------------------------DATE PICKER 2
    public static class DatePickerFragment2 extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // set default date

            //Date Time FROM BEFORE
//            String date =  to_date_TV.getText().toString().trim();
//            String[] data = date.split("-", 3);
//            int year = Integer.parseInt(data[0]);
//            int month = Integer.parseInt(data[1])-1;    //Because January is 0
//            int day = Integer.parseInt(data[2]);

            //Date Time NOW
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), R.style.DatePicker2, this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // get selected date
            mYear2 = year;
            mMonth2 = month;
            mDay2 = day;
            // show selected date to date button
            date2 = mMonth2 + 1+"/"+mDay2+"/"+mYear2;
            to_date_TV.setText(date2);
//            to_date_TV.setText(new StringBuilder()
//                    .append(mMonth2 + 1).append("/")
//                    .append(mDay2).append("/")
//                    .append(mYear2).append(""));
        }
    }

    //----------------------------------------------------------DATE PICKER 2


    public void onBackPressed(){

        qty.clear();
        desc.clear();
        concat_order.clear();
        finish();
    }

    public void getMID(){
        //midQuery = "select M_ID from ORDER_MASTER where TO_CHAR(ENTRY_DATE,'dd/mm/yyyy') = TO_CHAR(SYSDATE,'dd/mm/yyyy')  AND ORG_ID = '"+LoginActivity.org+"' AND ENTRY_BY = '"+employeeNumber+"'";
        //midQuery = "select M_ID from ORDER_MASTER where ENTRY_BY = '"+employeeNumber+"' AND ENTRY_DATE BETWEEN TO_DATE('"+date1+"','mm/dd/yyyy') AND TO_DATE('"+date2+"','mm/dd/yyyy') ORDER BY M_ID DESC";
        midQuery = "select M_ID, TO_CHAR(ENTRY_DATE,'DD-MON-YYYY'),DAMAGED from ORDER_MASTER where ENTRY_BY = '"+employeeNumber+"' AND TO_DATE(ENTRY_DATE) BETWEEN TO_DATE('"+date1+"','mm/dd/yyyy') AND TO_DATE('"+date2+"','mm/dd/yyyy') ORDER BY M_ID DESC";
        mid.clear();
        order_date.clear();
        try {
            PreparedStatement preparedStatementMID = connection.prepareStatement(midQuery);
            ResultSet resultSetName = preparedStatementMID.executeQuery();

            while (resultSetName.next()) {
                mid.add(resultSetName.getString(1));
                order_date.add(resultSetName.getString(2));
                damage_stat.add(resultSetName.getString(3));
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
    //@Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
////        Calendar c = Calendar.getInstance();
////        c.set(Calendar.YEAR,year);
////        c.set(Calendar.YEAR,month);
////        c.set(Calendar.YEAR,dayOfMonth);
//        month = month +1;
//        currentDate = month+"/"+dayOfMonth+"/"+year;
//        from_date_TV.setText(currentDate);
//
//
//    }
//
//    private void showDatePickerDialogue(){
//            DatePickerDialog datePickerDialog = new DatePickerDialog(
//                    this,
//                    this,
//                    Calendar.getInstance().get(Calendar.YEAR),
//                    Calendar.getInstance().get(Calendar.MONTH),
//                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
//            );
//            datePickerDialog.show();
//
//    }


}
