package com.rajdeep.agastipharmaceuticals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EnterOtpActivity extends AppCompatActivity {
    EditText eOtp, eNewPassword, etxt_retype_password_reset_otp;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        setContentView(R.layout.activity_enter_otp);

        Intent i = getIntent();
        String randomNumber = i.getStringExtra("rand");
        String email = i.getStringExtra("email");
        int rand = Integer.parseInt(randomNumber);

        eOtp = findViewById(R.id.etxt_email_otp);
        eNewPassword = findViewById(R.id.etxt_password_reset_otp);
        button = findViewById(R.id.btn_set_new_pass);
        etxt_retype_password_reset_otp = findViewById(R.id.etxt_retype_password_reset_otp);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = eNewPassword.getText().toString();
                String repass = etxt_retype_password_reset_otp.getText().toString();
                if(password.equals(repass)) {
                    int otp = Integer.parseInt(eOtp.getText().toString());

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, DBClass.urlForgotPassword,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    System.out.println("This is response" + response);

                                    if(response.equals("{\"data\":[{\"result\":\"success\"}]}")) {
                                        if (otp == rand) {
                                            Toast.makeText(EnterOtpActivity.this, "Authentication Done Successfully!\nYour new password has been set", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(EnterOtpActivity.this, "Invalid OTP! Check again...", Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (response.equals("{\"data\":[{\"result\":\"Failed to update the data\"}]}")) {
                                        Toast.makeText(EnterOtpActivity.this, "Something went wrong, Failed to update the data!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(EnterOtpActivity.this, "Not a registered E-mail!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("newpassword", password);
                            params.put("email", email);

                            Log.e("Params", params.toString());
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
                else{
                    Toast.makeText(EnterOtpActivity.this, "Passwords must be same", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

    }
}