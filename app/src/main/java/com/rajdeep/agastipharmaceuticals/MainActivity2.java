package com.rajdeep.agastipharmaceuticals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        setContentView(R.layout.activity_main2);


        textView = findViewById(R.id.textView);
        textView.setText("Order Placed Successfully!\n The order will be displayed on 'My Orders' tab soon.");

        // Unwrapping --> Intent --> Bundle --> ArrayList
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<DataMetaData> takenData = (ArrayList<DataMetaData>) args.getSerializable("takenData");

        /*
        * The warning message you are seeing indicates that you are performing an unchecked cast from a Serializable object to an ArrayList<DataMetaData> object. This means that you are trying to cast an object of one type to another type, but the compiler cannot guarantee that the cast is safe at runtime.
        To fix this warning, you need to perform a safe cast from the Serializable object to the ArrayList<DataMetaData> object. Here's an example code snippet that demonstrates how to do this:*/


        // Sending data to PHP:-
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DBClass.urlNewOrders, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // handle response
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handle error
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // modify return type to Map<String, String>
                Map<String, String> params0 = new HashMap<>();

                // Using DB for accessing the `cid`
                MyDBHelper myDBHelper = new MyDBHelper(MainActivity2.this);
                String[] stringArray = myDBHelper.fetchData();

                for (int i = 0; i < takenData.size(); i++) {
                    Map<String, String> params1 = new HashMap<>();
                    params1.put("count", String.valueOf(takenData.size()));
                    params1.put("cid", stringArray[4]);
                    params1.put("quantity" + (i + 1), takenData.get(i).quantity);
                    params1.put("product_id" + (i + 1), takenData.get(i).product_id);

                    params0.putAll(params1);
                }
                return params0;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


//        for (int i = 0; i < takenData.size(); i++) {
//            System.out.println("--------------");
//            System.out.println("QUANTITY : " + takenData.get(i).quantity + "MEDICINE NAME : " + takenData.get(i).medicineName + "POSITION : "  + takenData.get(i).position);
//            System.out.println("--------------");
//        }
//        Toast.makeText(this, "" + takenData.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

    }

}