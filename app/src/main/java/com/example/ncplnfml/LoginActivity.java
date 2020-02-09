package com.example.ncplnfml;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    private static final String DEFAULT_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DEFAULT_URL = "jdbc:oracle:thin:@10.0.0.6:1521/orcl"; //CWPL IP
   // private static final String DEFAULT_URL = "jdbc:oracle:thin:@163.47.147.74:1521/cwpl.mj-group.com";   //Real IP
    private static String DEFAULT_USERNAME = "RSSALES";
    private static String DEFAULT_PASSWORD = "123";

    private Connection connection;
    private boolean connectivity;

    private Statement statement;
    private String query;

    private String employeeNumber;
    private String employeeName;
    private String orgId;

    //edit text and buttons

    private EditText userName;
    private EditText password;
    private Button signinbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        initializeConnection();

        userName = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signinbtn = (Button) findViewById(R.id.signinbtn);

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(userName.getText().toString())) {
                    userName.setError("Enter Username");
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("Enter Password");
                } else {
                    if (connectivity) {

                        query = "select EMPLOYEE_NUMBER, EMPLOYEE_NAME, ORG_ID from USERS where USERNAME = '"
                                + userName.getText().toString() + "' AND PASSWORD = '" + password.getText().toString() + "'";

                        try {
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            ResultSet resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                employeeNumber = resultSet.getString(1);
                                employeeName = resultSet.getString(2);
                                orgId = resultSet.getString(3);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        if (employeeNumber != null) {

                            Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                            intent.putExtra("EMPLOYEE_NUMBER", employeeNumber);
                            intent.putExtra("EMPLOYEE_NAME", employeeName);
                            intent.putExtra("ORG_ID", orgId);

                            startActivity(intent);



                            userName.setText(null);
                            password.setText(null);
                            finish();
                        }else{
                            print("Invalid user name or password");
                        }
                    }else{
                        print("Connection error check internet connection and try again.");
                        //initializeConnection();
                    }


                }
            }

        });


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
            statement = connection.createStatement();
            connectivity = true;
            Toast.makeText(this, "Connected.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            connectivity = false;
            Toast.makeText(this, "Connection error check internet connection and try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    public  void print(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }


}
