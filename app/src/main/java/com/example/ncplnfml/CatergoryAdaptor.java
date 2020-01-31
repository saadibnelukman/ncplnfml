package com.example.ncplnfml;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.ncplnfml.CategoryActivity.getList;

public class CatergoryAdaptor extends RecyclerView.Adapter<CatergoryAdaptor.MyViewHolder> {

    private static ClickListener clickListener;
    Context context;
    private List<String> category;

    public CatergoryAdaptor(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.category_card,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.categoryTextView.setText(getList().get(position));





    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView categoryTextView;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTextView = itemView.findViewById(R.id.categoryTextView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(),v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(),v);
            return false;
        }
    }

    public interface ClickListener{

        void onItemClick(int position,View v);
        void onItemLongClick(int position,View v);
    }

    public void setOnItemClickListener(ClickListener clickListener){
        CatergoryAdaptor.clickListener = clickListener;
    }
}
