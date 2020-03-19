package com.example.ncplnfml;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.ncplnfml.CategoryActivity.addToArray;
import static com.example.ncplnfml.CategoryActivity.deleteFromArray;
import static com.example.ncplnfml.CategoryActivity.findPosition;
import static com.example.ncplnfml.CategoryActivity.getAvaProducts;
import static com.example.ncplnfml.CategoryActivity.getOrderPID;
import static com.example.ncplnfml.CategoryActivity.getOrderProduct;
import static com.example.ncplnfml.CategoryActivity.getQtyProducts;
import static com.example.ncplnfml.CategoryActivity.removeOrderProducts;
import static com.example.ncplnfml.CategoryActivity.updateArray;
//import static com.example.ncplnfml.LoginActivity.hideKeyboard;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context context;
    //ArrayList<String> products = new ArrayList<>();
    Model[] products;
    public OrderAdapter(Context context,Model[] products) {
        this.context = context;
        this.products=products;
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
    public void onBindViewHolder(@NonNull final OrderAdapter.MyViewHolder holder, final int position) {

        //final Model product = products[position];

        holder.productName.setText(getOrderProduct().get(position));
        holder.qty.setText(getQtyProducts().get(position));
       //holder.qty.setEnabled(false);
        final String newQ = holder.qty.getText().toString();

        holder.qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

//                String newQ = s.toString();
//                Log.d("pos",""+position);
//              //  getQtyProducts().set(position,newQ);
//                //updateArray(holder.productName.getText().toString(),getOrderPID().get(position),holder.qty.getText().toString(),position);
//
//                int pos = findPosition(getOrderPID().get(position));
//                updateArray(getQtyProducts().get(position),getOrderPID().get(position),holder.qty.getText().toString(),pos);
                //Log.d("pos", String.valueOf(pos));
                Log.d("position", ""+position);
                if (getOrderPID().size() > position) {

                    int pos = findPosition(getOrderPID().get(position));

                    Log.d("pos", String.valueOf(pos));
                    Log.d("position", "" + position);
                    if (holder.qty.getText().toString().equals("") || s.toString().equals("")) {
                        holder.qty.setText("0");
                    }
//                if (Integer.parseInt(holder.qty.getText().toString()) > 0){
//                    holder.myCheckBox.setChecked(true);
                    // Model currentProduct=products[position];
                    if (Integer.parseInt(holder.qty.getText().toString()) > 0) {
                        // currentProduct.setSelected(true);
                        // checkedProducts.add(currentProduct);
                        if (pos >= 0) {
                            if (Integer.parseInt(getAvaProducts().get(pos)) < Integer.parseInt(holder.qty.getText().toString())) {

                                holder.qty.setText(getAvaProducts().get(pos));
                                updateArray(getOrderProduct().get(pos), getOrderPID().get(pos), holder.qty.getText().toString(), getAvaProducts().get(pos), pos);

                            }

                        }

                        //addQty(position,holder.qty.getText().toString());
                    } else {
                        // currentProduct.setSelected(false);
                        //checkedProducts.remove(currentProduct);
                        //removeOrderProducts(product.getProduct(),product.getPID(),holder.qty.getText().toString());
                        //deleteFromArray(pos);
                        //holder.submitBtn.setVisibility(View.INVISIBLE);

                        //holder.qty.setText(newQ);

                    }
                }

//                }
//                else{
//
//                }
            }








            @Override
            public void afterTextChanged(Editable s) {



            }
        });
//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.qty.setEnabled(true);
//                String newQ = holder.qty.getText().toString();
//                getQtyProducts().set(position,newQ);
//            }
//        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.qty.setEnabled(false);
                String item = getOrderProduct().get(position);
                String pid = getOrderPID().get(position);
                String qt = getQtyProducts().get(position);
                //removeOrderProducts(item,pid,qt);
                deleteFromArray(position);


                notifyDataSetChanged();
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,getOrderProduct().size());
                notifyItemRangeChanged(position,getOrderPID().size());

                Toast.makeText(context,"Removed : " + item,Toast.LENGTH_SHORT).show();

            }
        });

//        holder.qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    hideKeyboard(v);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return getOrderProduct().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName;
        EditText qty;
        Button deleteBtn,submitBtn;

        //Button edit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.product_name);
            qty = itemView.findViewById(R.id.qty);
            //edit = itemView.findViewById(R.id.edit_qty);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            submitBtn = itemView.findViewById(R.id.submitBtn);
        }
    }


}
