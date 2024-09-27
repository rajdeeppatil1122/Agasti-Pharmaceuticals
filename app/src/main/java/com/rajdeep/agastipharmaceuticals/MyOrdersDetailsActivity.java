package com.rajdeep.agastipharmaceuticals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyOrdersDetailsActivity extends AppCompatActivity {
    TextView txtAmount, txtDate, txtStatus, txtOrderNo;
    String sale_id;
    RecyclerView recyclerViewMyOrderDetails;
    RecyclerViewAdapterMyOrderDetails recyclerViewAdapterMyOrderDetails;
    ArrayList <MyOrderDetailsMetaData> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders_details);

        // Finding views & Binding...
        txtAmount = findViewById(R.id.txtAmount);
        txtDate = findViewById(R.id.txtDate);
        txtStatus = findViewById(R.id.txtStatus);
        txtOrderNo = findViewById(R.id.txtOrderNoOrderDetails);
        recyclerViewMyOrderDetails = findViewById(R.id.recyclerViewMyOrdersDetails);

        // Setting recyclerView layout using layoutManager...
        recyclerViewMyOrderDetails.setLayoutManager(new LinearLayoutManager(MyOrdersDetailsActivity.this));

        // Catching Intent...
        Intent intent = getIntent();
        sale_id = intent.getStringExtra("sale_id");
        String amount = intent.getStringExtra("amount");
        String date = intent.getStringExtra("date");
        String status = intent.getStringExtra("status");
        String position = intent.getStringExtra("position");

        // Setting the MyOrder views...
        txtAmount.setText("₹" + amount);
        txtDate.setText(date);
        txtStatus.setText(status);
        txtOrderNo.setText(position);
//        Toast.makeText(this, position, Toast.LENGTH_SHORT).show();


        // USING VOLLEY LIBRARY:
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DBClass.urlMyOrderDetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", ">> " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonData = jsonObject.getJSONArray("Data");

                            for (int i = 0; i < jsonData.length(); i++) {
                                MyOrderDetailsMetaData myOrderDetails = new MyOrderDetailsMetaData();
                                JSONObject jo = jsonData.getJSONObject(i);

                                myOrderDetails.medicineName = jo.getString("pname");
                                myOrderDetails.rate = jo.getString("rate");
                                myOrderDetails.quantity = jo.getString("subquantity");
                                myOrderDetails.payableAmount = jo.getString("subtotal");
                                myOrderDetails.discount = jo.getString("discount");
                                myOrderDetails.total = jo.getString("total");


                                arrayList.add(myOrderDetails);
                                recyclerViewAdapterMyOrderDetails = new RecyclerViewAdapterMyOrderDetails(MyOrdersDetailsActivity.this, arrayList);
                                recyclerViewMyOrderDetails.setAdapter(recyclerViewAdapterMyOrderDetails);
                            }


                        } catch (Exception e) {
                            Toast.makeText(MyOrdersDetailsActivity.this, "Exception : ", Integer.parseInt(e.toString())).show();
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
                params.put("sale_id", sale_id);

//                Log.e("Params", params.toString());
                return params;
            }
        };

        // Adding request to queue...
        RequestQueue requestQueue = Volley.newRequestQueue(MyOrdersDetailsActivity.this);
        requestQueue.add(stringRequest);


//        Toast.makeText(this, "The sale_id is : " + sale_id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

    }
}