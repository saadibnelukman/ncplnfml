package com.example.ncplnfml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.ncplnfml.HistoryActivity.desc;
import static com.example.ncplnfml.HistoryActivity.qty;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    Context context;


    public HistoryAdapter(Context context) {
        this.context = context;


    }


    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_card,null);
        HistoryAdapter.MyViewHolder holder=new HistoryAdapter.MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {

        holder.product_TV.setText(desc.get(position));
        holder.qty_TV.setText(qty.get(position));

    }

    @Override
    public int getItemCount() {
        return desc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView product_TV,qty_TV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            product_TV = itemView.findViewById(R.id.product_TV);
            qty_TV = itemView.findViewById(R.id.qty_TV);



        }
    }
}
