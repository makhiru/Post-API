package com.example.post_api.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.post_api.Models.Product_data;
import com.example.post_api.R;
import com.example.post_api.RecyclerClicklistner;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Product_Adapter extends RecyclerView.Adapter<Product_Adapter.ViewHolder> {

    List<Product_data.Productdatas> productdatasList;
    Context context;
    boolean all;
    RecyclerClicklistner clicklistner;

    public Product_Adapter(boolean all, List<Product_data.Productdatas> productdatasList, Context context, RecyclerClicklistner clicklistner) {
        this.productdatasList = productdatasList;
        this.clicklistner = clicklistner;
        this.all = all;
        this.context = context;
    }

    public Product_Adapter(boolean all, List<Product_data.Productdatas> productdatasList, Context context) {
        this.productdatasList = productdatasList;
        this.context = context;
        this.all = all;
    }

    @NonNull
    @Override
    public Product_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Product_Adapter.ViewHolder holder, int position) {

        String url = "https://hetsweb.000webhostapp.com/YouApp/" + productdatasList.get(position).getPRO_IMAGE();
        Picasso.get().load(url).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.image_placeholder).into(holder.img);
        holder.id.setText("" + productdatasList.get(position).getUID());
        holder.name.setText("" + productdatasList.get(position).getPRO_NAME());
        holder.price.setText("" + productdatasList.get(position).getPRO_PRICE());
        holder.des.setText("" + productdatasList.get(position).getPRO_DES());

        if (!all) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clicklistner.onLongClick(holder.itemView, productdatasList.get(holder.getAdapterPosition()));
                    System.out.println(productdatasList.get(holder.getAdapterPosition()).getID());
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productdatasList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name, price, des, id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.myprod_id);
            img = itemView.findViewById(R.id.myprod_img);
            name = itemView.findViewById(R.id.myprod_name);
            price = itemView.findViewById(R.id.myprod_price);
            des = itemView.findViewById(R.id.myprod_des);

        }
    }
}
