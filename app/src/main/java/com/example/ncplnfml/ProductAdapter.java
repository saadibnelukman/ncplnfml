package com.example.ncplnfml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import  android.text.Editable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.example.ncplnfml.CategoryActivity.addQty;
import static com.example.ncplnfml.CategoryActivity.addToArray;
import static com.example.ncplnfml.CategoryActivity.deleteFromArray;
import static com.example.ncplnfml.CategoryActivity.getList;
//import static com.example.ncplnfml.ProductActivity.addModel;
//import static com.example.ncplnfml.ProductActivity.getProduct;
//import static com.example.ncplnfml.ProductActivity.getProducts;

//import static com.example.ncplnfml.ProductActivity.getProduct;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyNewViewHolder> {
    Context context;
    public static ArrayList<String> product;
    private int counter;
    Button iBtn,dBtn;
    //private static ClickListenerProduct clickListenerProduct;
    Model[] products;
   // ArrayList<Model> products=new ArrayList<>();
    ArrayList<Model> checkedProducts=new ArrayList<>();



   // List<Boolean> itemList = new ArrayList<>();
    //String checkedProduct;

    public ProductAdapter(Context context,Model[] products) {
        this.context = context;
        this.products = products;

    }



    @NonNull
    @Override
    public MyNewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card,null);
        MyNewViewHolder holder=new MyNewViewHolder(v);
        return holder;



    }

    @Override
    public void onBindViewHolder(@NonNull final MyNewViewHolder holder, final int position) {
         final Model product=products[position];
        holder.productTextView.setText(product.getProduct());
        //holder.productTextView.setText(getProduct().get(position));
      holder.myCheckBox.setChecked(product.isSelected());


//      holder.iBtn.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               counter=Integer.parseInt(qty.getText().toString());
//               counter++;
//               qty.setText(String.valueOf(counter));
//               notifyDataSetChanged();
//           }
//       });
//
//        holder.dBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                counter=Integer.parseInt(qty.getText().toString());
//                counter--;
//                qty.setText(String.valueOf(counter));
//                notifyDataSetChanged();
//            }
//        });
//        Integer.parseInt(holder.qtyBtn.getNumber())
//        final int Eqty = Integer.parseInt(qty.getText().toString());

//         && Eqty != 0
//        else {
//            myCheckBox.setChecked(false);
//        }

        holder.setItemClickListener(new MyNewViewHolder.ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox myCheckBox= (CheckBox) v;
                Model currentProduct=products[pos];

                if(myCheckBox.isChecked() && !holder.qty.getText().toString().equals("0") ) {
                    currentProduct.setSelected(true);
                    checkedProducts.add(currentProduct);
//                    addModel();

//                    Model product=checkedProducts.get(pos);
                    addToArray(product.getProduct(),product.getPID());
                    addQty(pos,holder.qty.getText().toString());
//                    holder.qtyBtn.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
//                        @Override
//                        public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
//                            addQty(position,newValue+"");
//                        }
//                    });




                }
                else if(!myCheckBox.isChecked()) {
                    currentProduct.setSelected(false);
                    checkedProducts.remove(currentProduct);
                    deleteFromArray(product.getProduct(),product.getPID(),holder.qty.getText().toString());
                }
                else {
            myCheckBox.setChecked(false);
        }
            }
        });





    }


    @Override
    public int getItemCount() {
        return products.length;
//        return getProduct().size();
    }

    static class MyNewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView productTextView;

        CheckBox myCheckBox;

        ItemClickListener itemClickListener;
        //ClickListenerProduct clickListenerProduct;
        ElegantNumberButton qtyBtn;
        EditText qty;

        Button iBtn,dBtn;

        public MyNewViewHolder(@NonNull View itemView) {
            super(itemView);

            productTextView = itemView.findViewById(R.id.productTextView);
            //itemView.setOnClickListener(this);
            myCheckBox= itemView.findViewById(R.id.checkbox);
            myCheckBox.setOnClickListener(this);

            qty = itemView.findViewById(R.id.qty);
            iBtn = itemView.findViewById(R.id.iBtn);
            dBtn = itemView.findViewById(R.id.dBtn);
            //qtyBtn.setOnClickListener(this);

            String eText = qty.getText().toString();



            iBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int counter;
                    counter=Integer.parseInt(qty.getText().toString());
                    counter++;
                    qty.setText(String.valueOf(counter));
//                    MyNewViewHolder.this.notifyAll();

                }
            });

            dBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int counter;
                    counter=Integer.parseInt(qty.getText().toString());
                    if (counter>0){
                        counter--;
                    }
                    qty.setText(String.valueOf(counter));
//                    notifyDataSetChanged();
//                    MyNewViewHolder.this.notifyAll();
                }
            });



        }
        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }
        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
        public interface ItemClickListener {

            void onItemClick(View v,int pos);
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

//    public static Model[] getProducts() {
//
//
////        Model model[] = new Model[product.size()];
////        for (int j = 0; j < product.size(); j++) {
////
////            // Assign each value to String array
////            model[j] = product.get(j);
////        }
//
//        Model[] model={
//                new Model("Rumi"),
//                new Model("Anthony De Mello"),
//                new Model("Eckhart Tolle"),
//                new Model("Meister Eckhart"),
//                new Model("Mooji"),
//                new Model("Confucius"),
//                new Model("Francis Lucille"),
//                new Model("Thich Nhat Hanh"),
//
//        };
//
//        return model;
//    }


}

