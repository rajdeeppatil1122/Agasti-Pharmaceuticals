package com.rajdeep.agastipharmaceuticals;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    EditText etxtPassword, etxtConfirmPassword, etxtAddress, etxtContactNumber, etxtEmail, etxtName;
    Button btnSignUp;
    Boolean allFieldsProper;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        setContentView(R.layout.activity_sign_up);

        etxtPassword = findViewById(R.id.etxt_password);
        etxtConfirmPassword = findViewById(R.id.etxt_confirm_password);
        etxtAddress = findViewById(R.id.etxt_address);
        etxtContactNumber = findViewById(R.id.etxt_contact_no);
        etxtEmail = findViewById(R.id.etxt_email);
        btnSignUp = findViewById(R.id.btn_sign_up);
        etxtName = findViewById(R.id.etxt_name);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allFieldsProper = true;
                checkAllFields();
//                progressDialog.show();

                if (allFieldsProper) {
//                    Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
//                    startActivity(i);

                    Toast.makeText(SignUpActivity.this, "Account Has Been Created Successfully!", Toast.LENGTH_LONG).show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, DBClass.urlSignup,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    JSONObject jsonObject = null;
                                    Log.d("Response", ">> " + response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
//                                    progressDialog.dismiss();
                                    Log.e("Exception onERRORR.", error.toString());
//                        Toast.makeText(getApplicationContext(), "Check Internet Connection...", Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("name", etxtName.getText().toString());
                            params.put("email", etxtEmail.getText().toString());
                            params.put("contactNo", etxtContactNumber.getText().toString());
                            params.put("password", etxtPassword.getText().toString());
                            params.put("address", etxtAddress.getText().toString());


                            Log.e("Params", params.toString());
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }

                else {
                    // Do nothing
                }
            }
        });

    }

    public void checkAllFields() {
        if (etxtName.length() == 0) {
            allFieldsProper = false;
            etxtName.setError("This field is required");
        }
        String pass1 = etxtPassword.getText().toString();
        String pass2 = etxtConfirmPassword.getText().toString();

        if (!(pass1.equals(pass2))) {
            allFieldsProper = false;
            etxtPassword.setError("Password & Confirm Password must be same");
            etxtConfirmPassword.setError("Password & Confirm Password must be same");
        }
        if (etxtAddress.length() < 10){
            allFieldsProper = false;
            etxtAddress.setError("Enter your full address");
        }
        if(etxtContactNumber.length() != 10){
            allFieldsProper = false;
            etxtContactNumber.setError("Enter your proper contact number");
        }
        if((!(etxtEmail.getText().toString().contains("@"))) || (etxtEmail.length() == 1) || (etxtEmail.length() == 2)){
            allFieldsProper = false;
            etxtEmail.setError("Enter proper E-mail ID");
        }
        if((pass1.equals("") && pass2.equals("")) || (pass1.contains("        ") || pass2.contains("        "))){
            allFieldsProper = false;
            etxtPassword.setError("Password cannot be blank");
            etxtConfirmPassword.setError("Password cannot be blank");
        }
        if(pass1.length() < 8){
            allFieldsProper = false;
            etxtPassword.setError("Password cannot be less than 8 characters");
        }
        if(pass2.length() < 8){
            allFieldsProper = false;
            etxtConfirmPassword.setError("Password cannot be less than 8 characters");
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

    }
}