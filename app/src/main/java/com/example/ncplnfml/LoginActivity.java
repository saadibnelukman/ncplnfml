package com.example.ncplnfml;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class LoginActivity extends AppCompatActivity {

    private static final String DEFAULT_DRIVER = "oracle.jdbc.driver.OracleDriver";
//    private static final String DEFAULT_URL = "jdbc:oracle:thin:@10.0.0.3:1521/orcl";
    private static final String DEFAULT_URL = "jdbc:oracle:thin:@192.168.200.108:1521/HRDATA";
    private static String DEFAULT_USERNAME = "RSSALES";
    private static String DEFAULT_PASSWORD = "123";

    private Connection connection;
    private boolean connectivity;

    private Statement statement;
    private String query;

    public static String employeeNumber;
    public static String employeeName;
    public static String orgId;
    public static String customer_id;
    public static String user_type;


    //edit text and buttons

    private TextInputLayout userName,password;


//    private EditText userName;
//    private EditText password;
    private Button signinbtn;

    MaterialSpinner spinner;
    List<String> listItems = new ArrayList<>();
    ArrayAdapter<String> adapter;
    public static String org;

    String orgName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //closeKeyboard();
        initializeConnection();

        userName =  findViewById(R.id.username);
        password =  findViewById(R.id.password);
        signinbtn = findViewById(R.id.signinbtn);
        spinner = findViewById(R.id.orgSpinner);


        String orgQuery = "select DISTINCT(ORG_ID) from INVENTORY_ITEM";
        try {
            initializeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(orgQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orgId = resultSet.getString(1);

                if(orgId.equals("547")){
                    orgName = "Northern Flour Mills Limited";
                }
                if(orgId.equals("549")){
                    orgName = "Northern Consumer Products Limited";
                }
                listItems.add(orgName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //listItems.add(orgName);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,listItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != -1){

                    org = spinner.getItemAtPosition(position).toString();

                    if (spinner.getItemAtPosition(position).toString().equals("Northern Flour Mills Limited")){
                        org = "547";
                    }else if(spinner.getItemAtPosition(position).toString().equals("Northern Consumer Products Limited")){
                        org = "549";
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(userName.getEditText().getText().toString())) {
                    userName.setError("Enter Username");
                } else if (TextUtils.isEmpty(password.getEditText().getText().toString())) {
                    password.setError("Enter Password");
                }else {
                    if (connectivity) {

                        Log.d("username","username: "+userName.getEditText().getText().toString() );
                        Log.d("username","pass: "+password.getEditText().getText().toString() );
                        Log.d("username","org: "+org );

//                        query = "select EMPLOYEE_NUMBER, EMPLOYEE_NAME, CUSTOMER_ID from USERS where USERNAME = '"
//                                + userName.getEditText().getText().toString() + "' AND PASSWORD = '" + password.getEditText().getText().toString() + "' AND ORG_ID = '" +org+ "'";
//                        query = "select 1 INFO, CUSTOMER_NAME NAME, CUSTOMER_ID from CUSTOMER_INFO where CUSTOMER_ID = '" + userName.getEditText().getText().toString() + "' AND PASSWORD = '" + password.getEditText().getText().toString() + "' AND ORG_ID = '" +org+ "' UNION select 2 INFO, EMPLOYEE_NAME NAME, CUSTOMER_ID from USERS where USERNAME = '" + userName.getEditText().getText().toString() + "' AND PASSWORD = '" + password.getEditText().getText().toString() + "' AND ORG_ID = '" +org+ "'";
//                      query = "select 1 INFO, CUSTOMER_NAME NAME, CUSTOMER_ID from CUSTOMER_INFO where CUSTOMER_ID = '"+ userName.getEditText().getText().toString().trim()+"' AND PASSWORD = '"+ password.getEditText().getText().toString().trim()+"' AND ORG_ID = '"+ org +"' UNION select 2 INFO, EMPLOYEE_NAME NAME, CUSTOMER_ID from USERS where USERNAME = '"+ userName.getEditText().getText().toString().trim()+"' AND PASSWORD = '"+ password.getEditText().getText().toString().trim()+"' AND ORG_ID = '"+ org +"'";
                      query = "select 1 INFO,CUSTOMER_ID EMPLOYEE_NUMBER,CUSTOMER_NAME EMPLOYEE_NAME,CUSTOMER_ID from CUSTOMER_INFO where CUSTOMER_ID = '"+userName.getEditText().getText().toString()+"' AND PASSWORD = '"+password.getEditText().getText().toString()+"' AND ORG_ID = '"+org+"' UNION select 2 INFO,EMPLOYEE_NUMBER EMPLOYEE_NUMBER,EMPLOYEE_NAME EMPLOYEE_NAME, CUSTOMER_ID from USERS where EMPLOYEE_NUMBER = '"+userName.getEditText().getText().toString()+"' AND PASSWORD = '"+password.getEditText().getText().toString()+"' AND ORG_ID = '"+org+"'";
                        employeeNumber = null;
                        try {
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            ResultSet resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                user_type = resultSet.getString(1);
                                employeeNumber = resultSet.getString(2);
                                employeeName = resultSet.getString(3);
                                customer_id = resultSet.getString(4);
                                //orgId = resultSet.getString(3);
                            }
                        } catch (SQLException e) {
                            employeeNumber = null;
                            e.printStackTrace();
                        }

                        if (employeeNumber != null) {

                            Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                            //intent.putExtra("EMPLOYEE_NUMBER", employeeNumber);
                            intent.putExtra("CUSTOMER_ID", customer_id);
                            intent.putExtra("EMPLOYEE_NAME", employeeName);
                            intent.putExtra("ORG_ID", org);

                            startActivity(intent);



                            userName.getEditText().setText(null);
                            password.getEditText().setText(null);
                            finish();
                        }else{
                            print("Invalid user name or password");
                        }
                    }else{
                        print("Connection error check internet connection and try again.");
                        initializeConnection();
                    }


                }
            }

        });


        userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }


    private static Connection createConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static Connection createConnection() throws ClassNotFoundException, SQLException {

        return createConnection(DEFAULT_DRIVER, DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }
    public void initializeConnection(){
        try {
            this.connection = createConnection();
//            statement = connection.createStatement();
            connectivity = true;

            //showAlertBox("Connection","You are Connected");
//            Toast.makeText(this, "Connected.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            connectivity = false;
            Toast.makeText(this, "Connection error check internet connection and try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    public  void print(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void showAlertBox(String title, String msg) {
        new AlertDialog.Builder(LoginActivity.this)
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

