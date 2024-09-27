package com.rajdeep.agastipharmaceuticals;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewOrdersFragment extends Fragment implements RecyclerViewAdapterNewOrder.OnTextChangedListener {
    RecyclerViewAdapterNewOrder recyclerViewAdapterNewOrder;
    RecyclerView recyclerView;
    Button btnAddNewOrder, btnRemoveNewOrder, btnPlaceOrder;
    TextView txtPrice;
    EditText editText;
    View view1, view2;
    int newOrderCounter = 1;
    ArrayList<DataMetaData> arrData = new ArrayList<>();
    ArrayList<NewOrderMetaData> arrOrders = new ArrayList<>();
    int textChangedCounter = 0;
    ArrayList<String> medArrayList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_new_orders, container, false);
        view2 = inflater.inflate(R.layout.recycler_row_new_order, container, false);

        recyclerView = view1.findViewById(R.id.recyclerViewNewOrders);
        btnAddNewOrder = view1.findViewById(R.id.add_one_more_order_btn);
        btnPlaceOrder = view1.findViewById(R.id.place_order_btn);
        txtPrice = view1.findViewById(R.id.txtTotalAmount);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        medArrayList.add("Med1");
//        medArrayList.add(("Med2"));

        // Fetching the list of products:
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DBClass.urlProducts,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonData = jsonObject.getJSONArray("Data");

                            System.out.println(response);

                            for (int i = 0; i < jsonData.length(); i++) {
                                JSONObject jo = jsonData.getJSONObject(i);

                                if (!(medArrayList.contains(jo.get("pname")))) {
                                    medArrayList.add(jo.getString("pname"));

                                }
                            }
                            // adding new card from here (where the proper elements from the spinner will be displayed)
                            addNewOrderMethod();
//                            System.out.println(med[0]);


                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Exception: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Exception onERRORR.", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        /// !!!!!----- PROBLEM ------!!!!! Problem happening here is that before completing the volley library code, the control comes downside and puts the empty list of array for the first card.
        // Using a thread for delaying the task will not help us because the setOnTextChangedListener will give an error that it only wants to come outside of any inner function.


        btnAddNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrOrders.add(new NewOrderMetaData(medArrayList));       // Here, the medArrayList will remain the same (in size), but the arrOrders ArrayList will continue growing in size whenever the new order (card) will get added.
                recyclerViewAdapterNewOrder.notifyItemInserted(newOrderCounter);
                newOrderCounter++;
            }
        });


        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                editText = view2.findViewById(101);
//                recyclerViewAdapterNewOrder.notify();
//                recyclerViewAdapterNewOrder.notifyItemChanged(100);
//                recyclerViewAdapterNewOrder.notifyDataSetChanged();

                if (arrData.size() == 0) {
                    Toast.makeText(getContext(), "Please select a Medicine to place an order", Toast.LENGTH_LONG).show();
                } else {
                    // ALERT DIALOG:-
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(getContext());

                    //Setting message manually and performing action on button click
                    builder.setMessage("Are you sure to place selected medicines?")
                            .setCancelable(false)
                            .setPositiveButton("Place Order", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    // WE are wrapping arraylist into bundle and bundle into intent... (for passing arraylist from here to mainactivity2)
                                    Intent i = new Intent(getContext(), MainActivity2.class);
                                    Bundle args = new Bundle();
                                    args.putSerializable("takenData", (Serializable) arrData);
                                    i.putExtra("BUNDLE", args);
                                    startActivity(i);
                                }
                            })
                            .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Re-confirm");
                    alert.show();
                }
            }

            // System.out.println("SIZE OF arrDATA ---> : " + arrData.size());
        });


        // Here, when the text will get changed, the below method will get executed...

        // NOTE: The first CARD which will be visible will be fired from here (from below 5 line)

        arrOrders.add(new NewOrderMetaData(medArrayList));      // REMEMBER: This will create ArrayList inside an arraylist
        recyclerViewAdapterNewOrder = new RecyclerViewAdapterNewOrder(getContext(), medArrayList, arrOrders);
        recyclerView.setAdapter(recyclerViewAdapterNewOrder);


        recyclerViewAdapterNewOrder.setOnTextChangedListener((this));

        // removing and adding a new card where all data is properly displayed into the spinner (so, we will never use the card no. 1, we will take the data from card2)
        removeNewOrder();


        return view1;
    }


    @Override
    public void onTextChanged(ArrayList<DataMetaData> arrData, int cardToBeDeletedIndex, boolean cardDeleteOrNot) {    // taking arraylist from recyclerview

        System.out.println("cardToBeDeletedIndex = : " + cardToBeDeletedIndex + "  cardDeletedOrNot = : " + cardDeleteOrNot);
//        System.out.println("<>>>>>>>>>>>>>>>>>>><<<<<<<<<<<,, " + arrData.get(0).product_id);

        if (textChangedCounter == 0) {
            this.arrData = arrData;
        }

        else if(cardDeleteOrNot == true){
            try {
                this.arrData.remove(cardToBeDeletedIndex);
                newOrderCounter--;      // we need to maintain a counter because it is used while adding new card...
                arrOrders.remove(newOrderCounter - 1);      // this idiot is not necessary, this arraylist just holds the same names of medicines.
                recyclerViewAdapterNewOrder.notifyItemRemoved(cardToBeDeletedIndex);
            }
            catch (IndexOutOfBoundsException e2){
                Toast.makeText(getContext(), "Place your medicine here", Toast.LENGTH_SHORT).show();
            }
        }

        else {
            ArrayList<DataMetaData> tempArray = new ArrayList<>();      // tempArrayList is created to compare it with the previously created arrData and checks if there is any index of ArrayList that has holderPosition as same as the tempArrayList (tempArray). If same, that means the user has edited the view and instead of adding the data on the new index we have to edit the data present at the index itself.
            tempArray = arrData;
            boolean flag = false;
            for (int i = 0; i < this.arrData.size(); i++) {
                if (this.arrData.get(i).position == tempArray.get(0).position) {
                    this.arrData.get(i).quantity = tempArray.get(0).quantity;
                    this.arrData.get(i).medicineName = tempArray.get(0).medicineName;
                    this.arrData.get(i).product_id = tempArray.get(0).product_id;
                    this.arrData.get(i).price = tempArray.get(0).price;
                    flag = true;
                    break;
                }
            }
            if (!flag) {      // if the flag is false that means if a new holder was created and data was added in it, in that case, create a new index for arrData and fill the data of new index in it as follows...
                this.arrData.add(new DataMetaData(arrData.get(0).position, arrData.get(0).quantity, arrData.get(0).medicineName, arrData.get(0).product_id, arrData.get(0).price));
            }
        }


        System.out.println("---------------------------------------");
        int total = 0;
        for (int i = 0; i < this.arrData.size(); i++) {

            total = total + Integer.parseInt(this.arrData.get(i).price);
            System.out.println("ARRAYLIST INDEX : " + i + " HOlDER Position : " + this.arrData.get(i).position + " QUANTITY : " + this.arrData.get(i).quantity + " MEDICINE Name : " + this.arrData.get(i).medicineName + " PRODUCT_ID : " + this.arrData.get(i).product_id);

            if(i == this.arrData.size()-1){
//                Toast.makeText(getContext(), "SET" + this.arrData.get(i).price, Toast.LENGTH_SHORT).show();
                txtPrice.setText("TOTAL :   ₹" + String.valueOf(total));
            }
        }
        System.out.println("---------------------------------------");

        textChangedCounter++;
    }

//    @Override
//    public void onItemClick(ArrayList<Object> data) {
//
//    }
// 
//    @Override
//    public void onItemClick(String data) {
//        data2 = data;
//        System.out.println("I am in Fragment and the PASSED DATA is : " + data);
//        Log.d("DATA : ", data);
//    }



    public void addNewOrderMethod(){
        arrOrders.add(new NewOrderMetaData(medArrayList));       // Here, the medArrayList will remain the same (in size), but the arrOrders ArrayList will continue growing in size whenever the new order (card) will get added.
        recyclerViewAdapterNewOrder.notifyItemInserted(newOrderCounter);
        newOrderCounter++;
    }

    public void removeNewOrder(){
            arrOrders.remove(newOrderCounter - 1);
            recyclerViewAdapterNewOrder.notifyItemRemoved(newOrderCounter - 1);
            newOrderCounter--;
    }


}