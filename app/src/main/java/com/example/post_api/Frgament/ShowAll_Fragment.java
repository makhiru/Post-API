package com.example.post_api.Frgament;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.post_api.Adapter.Product_Adapter;
import com.example.post_api.Models.Delete_Products;
import com.example.post_api.Models.Product_data;
import com.example.post_api.R;
import com.example.post_api.RecyclerClicklistner;
import com.example.post_api.Retro;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAll_Fragment extends Fragment {

    RecyclerView recyclerView;
    Product_Adapter productAdapter;

    ProgressBar progressBar;

    List<Product_data.Productdatas> productdatasList;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showall, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);

        progressBar = view.findViewById(R.id.progressbar);
        Retro.getInstance().retroAPI.ALL_Product_Data().enqueue(new Callback<Product_data>() {
            @Override
            public void onResponse(Call<Product_data> call, Response<Product_data> response) {
                if (response.body().getConnection() == 1) {
                    if (response.body().getResult() == 1) {

                        productdatasList = response.body().getProductdata();

                        productAdapter = new Product_Adapter(true, productdatasList, getContext());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(productAdapter);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getContext(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Product_data> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });

        return view;
    }

}
