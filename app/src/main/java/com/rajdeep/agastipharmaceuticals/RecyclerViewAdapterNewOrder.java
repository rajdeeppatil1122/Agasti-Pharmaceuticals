package com.rajdeep.agastipharmaceuticals;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import java.util.logging.Handler;

public class RecyclerViewAdapterNewOrder extends RecyclerView.Adapter<RecyclerViewAdapterNewOrder.NeededViewHolder> {

    Context context;
    int cardToBeDeletedIndex = 0;
    boolean deleteCardOrNot = false;
    ArrayList<String> arrMedicines;
    ArrayList<NewOrderMetaData> arrNoOfAddOOrders;
    View view, view2;
    //    private OnItemClickListener onItemClickListener;
    private OnTextChangedListener onTextChangedListener;

    String string = "";
    int textChangeCounter = 0;


    public RecyclerViewAdapterNewOrder(Context context, ArrayList<String> arrMedicines, ArrayList<NewOrderMetaData> arrNoOfAddOOrders) {
        this.context = context;
        this.arrMedicines = arrMedicines;       // for getting medicines list
        this.arrNoOfAddOOrders = arrNoOfAddOOrders;     // for maintaining the size of the the views added on recyclerview
    }

    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
        this.onTextChangedListener = onTextChangedListener;
    }

    public interface OnTextChangedListener {
        void onTextChanged(ArrayList<DataMetaData> arrData, int cardToBeDeletedIndex, boolean cardDeleteOrNot);
    }


    public interface OnItemClickListener {
        //        void onItemClick(ArrayList<Object> data);
        void onItemClick(String data);
    }

//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }

    @NonNull
    @Override
    public RecyclerViewAdapterNewOrder.NeededViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.recycler_row_new_order, parent, false);
        view2 = layoutInflater.inflate(R.layout.fragment_new_orders, parent, false);

        RecyclerView.ViewHolder viewHolder = new NeededViewHolder(view);

        return (NeededViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterNewOrder.NeededViewHolder holder, int position) {
        int randomNumberEditText = 100; // we are going to set IDs of EditText starting from 100
        int randomNumberSpinner = 200; // we are going to set IDs starting from 100

        final ArrayList[] arrayList = new ArrayList[1];
        final ListView[] listView = {null};


        final StringWrapper[] selectedItemWrapper = {new StringWrapper()};

        holder.editTextQuantity.setEnabled(false);
        holder.editTextQuantity.setTextColor(Color.parseColor("#808080"));

        holder.spinnerNameOfMedicineNowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog;

                // Initialize dialog
                dialog=new Dialog(context);

                // set custom dialog
                dialog.setContentView(R.layout.searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(650,800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                listView[0] = dialog.findViewById(R.id.list_view);

                // Initialize array adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrMedicines);

                // set adapter
                listView[0].setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                listView[0].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        holder.spinnerNameOfMedicineNowText.setText(adapter.getItem(position).toString());
                        selectedItemWrapper[0].value = adapter.getItem(position);
                        System.out.println(">>>" + selectedItemWrapper[0].value);

                        // making edittext editable
                        holder.editTextQuantity.setEnabled(true);
                        holder.editTextQuantity.setTextColor(Color.BLACK);


                        // Fetching the meta-data of the list of products:
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, DBClass.urlProducts,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            JSONArray jsonData = jsonObject.getJSONArray("Data");

//                                            System.out.println("Response from recyclerview :" + response);

                                            for (int i = 0; i < jsonData.length(); i++) {
                                                JSONObject jo = jsonData.getJSONObject(i);

                                                String medName = adapter.getItem(position);
                                                if (medName.equals(jo.get("pname"))) {
                                                    holder.txtMedicinePrice.setText("₹" + jo.getString("retailrate"));
                                                    holder.txtDiscount.setText("₹0");
                                                    holder.txtPayablePrice.setText("₹" + jo.getString("retailrate"));
                                                }

                                                // Dismiss dialog
                                                dialog.dismiss();

                                            }

                                            // invoking
                                            String inputQuantity = holder.editTextQuantity.getText().toString();
                                            holder.editTextQuantity.setText(inputQuantity);


                                        } catch (Exception e) {
//                                    Toast.makeText(getContext(), "Exception: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                                            Log.d("Error meta-data", String.valueOf(e));
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

                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(stringRequest);

                    }
                });

            }
        });

        holder.txtOrderNumber.setText("Medicine Order : " + (position+1));

        holder.textViewAddQty.setText("Add Quantity here");

        if(position == 0) {
            holder.deleteImageView.setVisibility(View.INVISIBLE);
        }


        holder.editTextQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // Save the user input to your data structure
                String inputQuantity = holder.editTextQuantity.getText().toString();        // Taking EditText input

                // Resetting the price in multiples (when quantity is changed):
                StringRequest stringRequest = new StringRequest(Request.Method.POST, DBClass.urlProducts,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonData = jsonObject.getJSONArray("Data");

//                                    System.out.println("Response from recyclerview :" + response);

                                    for (int i = 0; i < jsonData.length(); i++) {
                                        JSONObject jo = jsonData.getJSONObject(i);
                                        System.out.println("Resetting.............");

                                        String medName = selectedItemWrapper[0].value;
                                        if (medName.equals(jo.get("pname"))) {
                                            String newMedPriceS = jo.getString("retailrate");
                                            int newMedPrice = Integer.parseInt(inputQuantity) * Integer.parseInt(newMedPriceS);
                                            holder.txtMedicinePrice.setText("₹" + String.valueOf(newMedPrice));
                                            holder.txtDiscount.setText("₹0");
                                            holder.txtPayablePrice.setText("₹" + String.valueOf(newMedPrice));
                                            break;
                                        }

                                    }

                                } catch (Exception e) {
//                                    Toast.makeText(getContext(), "Exception: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                                    Log.d("Error price multiples", String.valueOf(e));
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

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);

                holder.itemView.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (!inputQuantity.equals("") && !inputQuantity.equals("0")) {
                            ArrayList<DataMetaData> arrData = new ArrayList<>();

                            final String[] productId = new String[1];

                            // Finding the product ID:-
                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, DBClass.urlProducts,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonData = jsonObject.getJSONArray("Data");
                                                System.out.println("Called.............");

                                                int counter = 1;  // Flag to track if the block has been executed

//                                        System.out.println("Response from recyclerview :" + response);

                                                for (int i = 0; i < jsonData.length(); i++) {
                                                    JSONObject jo = jsonData.getJSONObject(i);

                                                    String medName = selectedItemWrapper[0].value;
                                                    System.out.println(medName);
                                                    if (medName.equals(jo.get("pname"))) {
                                                        productId[0] = jo.getString("product_id");
                                                        String price = holder.txtPayablePrice.getText().toString();
                                                        price = price.substring(1);
                                                        arrData.add(new DataMetaData(position, inputQuantity, medName, productId[0], price));
                                                        counter++;
                                                        break;
                                                    }
                                                }

                                                if (onTextChangedListener != null && counter == 2) {
                                                    System.out.println("COUNTER : " + counter);
                                                    onTextChangedListener.onTextChanged(arrData, cardToBeDeletedIndex, deleteCardOrNot);
                                                    deleteCardOrNot = false;    // resetting the value
                                                }


                                            } catch (Exception e) {
//                                    Toast.makeText(getContext(), "Exception: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                                                Log.d("Error Edit-text", String.valueOf(e));
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

                            RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                            requestQueue1.add(stringRequest1);

                        }


                    }
                }, 400);




            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }

        });

        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Here, what I am trying to do is: I am invoking the editTextQuantity view because that is the only one who can utilize the addTextChangedListener. So, I am waking it up so that whenever there will be any changes in spinner, it will get reflected too!
                cardToBeDeletedIndex = position;
                deleteCardOrNot = true;
                String inputQuantity = holder.editTextQuantity.getText().toString();
                holder.editTextQuantity.setText(inputQuantity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrNoOfAddOOrders.size();
    }

    public class NeededViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llRow;
        TextView spinnerNameOfMedicineNowText;
        EditText editTextQuantity;
        TextView textViewAddQty, txtMedicinePrice, txtDiscount, txtPayablePrice, txtOrderNumber;
        ImageView deleteImageView;
//        Button placeOrderBtn;

        public NeededViewHolder(@NonNull View itemView) {
            super(itemView);

            this.editTextQuantity = itemView.findViewById(R.id.txtQuantityNumber);
            this.spinnerNameOfMedicineNowText = itemView.findViewById(R.id.txtSpinnerSelectionNowTxt);
            this.llRow = itemView.findViewById(R.id.llRow);
            this.textViewAddQty = itemView.findViewById(R.id.txtAddQty);
            this.deleteImageView = itemView.findViewById(R.id.deleteImageBucket);

            this.txtMedicinePrice = itemView.findViewById(R.id.txtMedicinePrice);
            this.txtDiscount = itemView.findViewById(R.id.txtMedicineDiscount);
            this.txtPayablePrice = itemView.findViewById(R.id.txtPayableAmountrr);
            this.txtOrderNumber = itemView.findViewById(R.id.txtOrderNumber);

        }
    }

}



class StringWrapper {
    String value;
}
