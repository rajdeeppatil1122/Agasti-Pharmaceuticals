package com.rajdeep.agastipharmaceuticals;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterMyOrderDetails extends RecyclerView.Adapter<RecyclerViewAdapterMyOrderDetails.NeededViewHolder> {

    Context context;
    ArrayList<MyOrderDetailsMetaData> arrData;
    View view;


    public RecyclerViewAdapterMyOrderDetails(Context context, ArrayList<MyOrderDetailsMetaData> arrData) {
        this.context = context;
        this.arrData = arrData;
    }




    @NonNull
    @Override
    public RecyclerViewAdapterMyOrderDetails.NeededViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.recycler_row_my_order_details, parent, false);

        RecyclerView.ViewHolder viewHolder = new NeededViewHolder(view);

        return (NeededViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterMyOrderDetails.NeededViewHolder holder, int position) {

        holder.txtMedicineName.setText(arrData.get(position).medicineName);
        holder.txtRate.setText( "₹" + arrData.get(position).rate);
        holder.txtQuantity.setText(arrData.get(position).quantity);
        holder.txtPayableAmount.setText( "₹" + arrData.get(position).payableAmount);
        holder.txtDiscount.setText( "₹" + arrData.get(position).discount);
        holder.txtTotal.setText( "₹" + arrData.get(position).total);
        holder.txtCounter.setText("Medicine Number " + "[" + (position + 1) + "]");

    }

    @Override
    public int getItemCount() {
        return arrData.size();
    }

    public static class NeededViewHolder extends RecyclerView.ViewHolder {
        TextView txtMedicineName, txtRate, txtTotal, txtQuantity, txtPayableAmount, txtDiscount, txtCounter;
        LinearLayout linearLayout;

        public NeededViewHolder(@NonNull View itemView) {
            super(itemView);

            this.txtDiscount = itemView.findViewById(R.id.txtDiscount);
            this.txtRate = itemView.findViewById(R.id.txtRate);
            this.txtTotal = itemView.findViewById(R.id.txtTotal);
            this.txtPayableAmount = itemView.findViewById(R.id.txtPayableAmount);
            this.txtMedicineName = itemView.findViewById(R.id.txtMedicineName);
            this.txtQuantity = itemView.findViewById(R.id.txtQuantity);
            this.txtCounter = itemView.findViewById(R.id.txtCounter);

            this.linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutMyOrdersDetails);

        }
    }
}
