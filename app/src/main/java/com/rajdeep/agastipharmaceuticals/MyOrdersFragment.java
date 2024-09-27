package com.rajdeep.agastipharmaceuticals;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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


public class MyOrdersFragment extends Fragment {
    View view;
    RecyclerView recyclerViewMyOrders;
    RecyclerViewAdapterMyOrder recyclerViewAdapterMyOrder;
    TextView txtViewMyOrders;
    ArrayList<MyOrderMetaData> arrayList = new ArrayList<>();
//    arrDataToBeSentToRecyclerView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        MyDBHelper myDBHelper = new MyDBHelper(getContext());

        txtViewMyOrders = view.findViewById(R.id.txtViewMyOrders);
        recyclerViewMyOrders = view.findViewById(R.id.recyclerViewMyOrders);
        recyclerViewMyOrders.setLayoutManager(new LinearLayoutManager(getContext()));

//        arrayList.add(new MyOrderMetaData("Amount is: 100 Rs.", "1 March 2023", "PAID"));


        // USING VOLLEY LIBRARY:

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DBClass.urlMyOrders,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response My Orders", ">> " + response);

                        if(response.equals("{\"Data\":{\"result\":\"No Order\"}}")){
                            txtViewMyOrders.setVisibility(View.VISIBLE);
                            txtViewMyOrders.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                                    fr.replace(R.id.fragmentContainer, new NewOrdersFragment());
                                    fr.commit();
                                    // Here, I cannot create an object of HomePageActivity because object will not give the reference of the current fragment (MyOrdersFragment).
                                    // Creating a new object be like you are creating a new world and trying to switch the bottom bar of that world, but what you actually want is to just manipulate the parent class method to change the bottom bar. So, creating an object makes no sense. You directly have to access the method from the main class and get the dedired result.
                                    // It is as follows:

                                    ((HomePageActivity)getActivity()).changeBottomBarToNewOrders();

//                                    HomePageActivity obj = new HomePageActivity();
//                                    obj.changeBottomBarToNewOrders();
                                }
                            });
                        }
                        else {
                            txtViewMyOrders.setVisibility(View.GONE);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonData = jsonObject.getJSONArray("Data");

                                for (int i = 0; i < jsonData.length(); i++) {
                                    MyOrderMetaData myOrder = new MyOrderMetaData();
                                    JSONObject jo = jsonData.getJSONObject(i);

                                    myOrder.amount = jo.getString("total");
                                    myOrder.date = jo.getString("sale_date");
                                    myOrder.sale_id = jo.getString("sale_id");

                                    String modified = "Paid";
//                                    if (jo.getString("status").equals("Closed")) {
//                                        modified = "Paid";
//                                    } else {
//                                        modified = "Unpaid";
//                                    }
                                    myOrder.status = modified;


                                    arrayList.add(myOrder);
                                    recyclerViewAdapterMyOrder = new RecyclerViewAdapterMyOrder(getContext(), arrayList);
                                    recyclerViewMyOrders.setAdapter(recyclerViewAdapterMyOrder);
                                }


                            } catch (Exception e) {
                                Toast.makeText(getContext(), "Exception: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
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

                // getting cid:
                String cid = myDBHelper.getCid();
                System.out.println("CID : " + cid);
                params.put("cid", cid);
//                Log.e("Params", params.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        return view;

    }
}