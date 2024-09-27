package com.rajdeep.agastipharmaceuticals;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterMyOrder extends RecyclerView.Adapter<RecyclerViewAdapterMyOrder.NeededViewHolder> {

    Context context;
    ArrayList<MyOrderMetaData> arrData;
    View view;


    public RecyclerViewAdapterMyOrder(Context context, ArrayList<MyOrderMetaData> arrData) {
        this.context = context;
        this.arrData = arrData;
    }




    @NonNull
    @Override
    public RecyclerViewAdapterMyOrder.NeededViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.recycler_row_my_order, parent, false);

        RecyclerView.ViewHolder viewHolder = new NeededViewHolder(view);

        return (NeededViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterMyOrder.NeededViewHolder holder, int position) {

        holder.txtAmount.setText( "₹" + arrData.get(position).amount);
        holder.txtDate.setText(arrData.get(position).date);
        holder.txtStatus.setText(arrData.get(position).status);
        int pos = position + 1;
        holder.txtOrderNo.setText("Order No. [" + (position + 1) + "]");

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyOrdersDetailsActivity.class);
                intent.putExtra("sale_id", arrData.get(holder.getAdapterPosition()).sale_id);
                intent.putExtra("amount", arrData.get(holder.getAdapterPosition()).amount);
                intent.putExtra("date", arrData.get(holder.getAdapterPosition()).date);
                intent.putExtra("status", arrData.get(holder.getAdapterPosition()).status);
                intent.putExtra("position", "Order No. [" + String.valueOf(pos) + "]");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrData.size();
    }

    public static class NeededViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate, txtAmount, txtStatus, txtOrderNo;
        LinearLayout linearLayout;

        public NeededViewHolder(@NonNull View itemView) {
            super(itemView);

            this.txtAmount = itemView.findViewById(R.id.txtAmount);
            this.txtDate = itemView.findViewById(R.id.txtDate);
            this.txtStatus = itemView.findViewById(R.id.txtStatus);
            this.txtOrderNo = itemView.findViewById(R.id.txtOrderNo);
            this.linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutMyOrders);

        }
    }
}
