package com.example.post_api;

import android.view.View;

import com.example.post_api.Models.Product_data;

public interface RecyclerClicklistner {
    void onLongClick(View view, Product_data.Productdatas productdatas);
}
