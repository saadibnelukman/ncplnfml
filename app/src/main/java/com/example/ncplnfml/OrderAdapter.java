package com.example.ncplnfml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.ncplnfml.CategoryActivity.getOrderPID;
import static com.example.ncplnfml.CategoryActivity.getOrderProduct;
import static com.example.ncplnfml.CategoryActivity.getQtyProducts;
import static com.example.ncplnfml.CategoryActivity.removeOrderProducts;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> orderProducts = new ArrayList<>();
    public OrderAdapter(Context context) {
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
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, final int position) {



        holder.productName.setText(getOrderProduct().get(position));
        holder.qty.setText(getQtyProducts().get(position));



        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = getOrderProduct().get(position);
                String pid = getOrderPID().get(position);
                String qt = getQtyProducts().get(position);
                removeOrderProducts(item,pid,qt);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position,getOrderProduct().size());
                notifyItemRangeChanged(position,getOrderPID().size());

                Toast.makeText(context,"Removed : " + item,Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return getOrderProduct().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName,qty;
        Button deleteBtn,submitBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.product_name);
            qty = itemView.findViewById(R.id.qty);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            submitBtn = itemView.findViewById(R.id.submitBtn);
        }
    }
}
