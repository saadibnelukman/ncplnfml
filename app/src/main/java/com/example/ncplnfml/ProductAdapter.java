package com.example.ncplnfml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.ncplnfml.CategoryActivity.getList;
import static com.example.ncplnfml.ProductActivity.getProduct;

//import static com.example.ncplnfml.ProductActivity.getProduct;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyNewViewHolder> {
    Context context;
    //public static ArrayList<String> product;
    //private static ClickListenerProduct clickListenerProduct;

    public ProductAdapter(Context context) {
        this.context = context;

    }



    @NonNull
    @Override
    public MyNewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_card,parent,false);

        return new MyNewViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyNewViewHolder holder, final int position) {

        holder.productTextView.setText(getProduct().get(position));

    }

    @Override
    public int getItemCount() {
        return getProduct().size();
    }

    class MyNewViewHolder extends RecyclerView.ViewHolder {

        TextView productTextView;
        //ClickListenerProduct clickListenerProduct;

        public MyNewViewHolder(@NonNull View itemView) {
            super(itemView);

            productTextView = itemView.findViewById(R.id.productTextView);
            //itemView.setOnClickListener(this);


        }


    }

//    public interface ClickListenerProduct{
//
//        void onItemClick(int position,View v);
//
//    }
//    public void setOnItemClickListenerProduct(ClickListenerProduct clickListenerProduct){
//                ProductAdapter.clickListenerProduct = clickListenerProduct;
//    }




}
