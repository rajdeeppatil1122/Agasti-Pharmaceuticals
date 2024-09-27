package com.rajdeep.agastipharmaceuticals;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    ImageView loginImage;
    EditText editTextEmail, editTextPassword;
    Button btnCreateAccount, btnSignIn;
    Animation scaleAnime;
    TextView forget_pass;
    Boolean allFieldsProper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        setContentView(R.layout.activity_login);

        MyDBHelper myDBHelper = new MyDBHelper(this);

        loginImage = findViewById(R.id.login_logo);
        btnCreateAccount = findViewById(R.id.btn_create_account);
        btnSignIn = findViewById(R.id.btn_sign_in);
        editTextPassword = findViewById(R.id.etxt_password);
        editTextEmail = findViewById(R.id.etxt_email);
        forget_pass = findViewById(R.id.forget_pass);
        scaleAnime = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_anim_faster);

        loginImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginImage.startAnimation(scaleAnime);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loginImage.clearAnimation();
                    }
                }, 2000);
            }
        });

        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(i);
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allFieldsProper = false;
                checkAllFields();
                if (allFieldsProper) {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, DBClass.urlLogin,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("Response Login", ">> " + response);


                                    String upToNCharacters = response.substring(0, Math.min(response.length(), 28));  // {"data":[{"result":"success"

                                    if (upToNCharacters.equals("{\"data\":[{\"result\":\"success\"")) {
                                        editTextEmail.setText("");
                                        editTextPassword.setText("");

                                        ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                                        pd.setMessage("Logging In...");
                                        pd.show();

                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            JSONArray jsonData = jsonObject.getJSONArray("data");

                                            JSONObject jo = jsonData.getJSONObject(0);

                                            String cid = jo.getString("cid");
                                            String name = jo.getString("name");
                                            String email = jo.getString("email");
                                            String contactno = jo.getString("mobileno");
                                            String address = jo.getString("address");


                                            myDBHelper.addDetails(cid, name, email, contactno, address);
                                            SharedPreferences pref2 = getSharedPreferences("login", MODE_PRIVATE);      // Here, the object of shared preference is different, but we are taking the same reference (of login).
                                            SharedPreferences.Editor editor = pref2.edit();

                                            editor.putBoolean("flag", true);
                                            editor.apply();
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                pd.cancel();
                                                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }, 1300);

                                    }

                                    else if (response.equals("{\"data\":[{\"result\":\"Invalid Username or password\"}]}")) {
                                        Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                                    }

                                    else {
                                        Toast.makeText(getApplicationContext(), "This email ID is not registered, Please Register!", Toast.LENGTH_SHORT).show();
                                    }
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
                            params.put("password", editTextPassword.getText().toString());
                            params.put("email", editTextEmail.getText().toString());

                            Log.e("Params", params.toString());
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);


                } else {
                    // Do nothing
                }
            }
        });
    }

    public void checkAllFields() {
        int flag = 1;
        if (editTextEmail.length() == 0) {
            allFieldsProper = false;
            flag = 0;
            editTextEmail.setError("This field is required");
        }
        if (editTextPassword.length() == 0) {
            allFieldsProper = false;
            flag = 0;
            editTextPassword.setError("Enter Password");
        }
        if (flag == 1) {
            allFieldsProper = true;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Do you really want to exit Agasti App?\n");

        // Set Alert Title
        builder.setTitle("Exit App");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            super.onBackPressed();
        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }

}