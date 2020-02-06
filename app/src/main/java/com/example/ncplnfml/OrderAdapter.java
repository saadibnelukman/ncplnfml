package com.example.ncplnfml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.ncplnfml.ProductActivity.getOrderProduct;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> orderProducts = new ArrayList<>();
    public OrderAdapter(Context context, ArrayList<String> orderProducts) {
        this.context = context;
//        this.orderProducts = orderProducts;
    }
    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.order_card,parent,false);

        return new OrderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {

        holder.productName.setText(getOrderProduct().get(position));

    }

    @Override
    public int getItemCount() {
        return getOrderProduct().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.product_name);
        }
    }
}
