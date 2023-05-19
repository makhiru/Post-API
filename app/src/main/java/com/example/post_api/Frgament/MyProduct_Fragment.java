package com.example.post_api.Frgament;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProduct_Fragment extends Fragment {

    RecyclerView recyclerView;
    Product_Adapter productAdapter;
    List<Product_data.Productdatas> productdatasList;
    ProgressBar progressBar;

    SharedPreferences preferences;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myproduct, container, false);

        preferences = getContext().getSharedPreferences("Log_in_pref", Context.MODE_PRIVATE);
        progressBar = view.findViewById(R.id.progressbar);
        recyclerView = view.findViewById(R.id.recyclerview);

        Retro.getInstance().retroAPI.Product_Data(preferences.getString("Userid", "1")).enqueue(new Callback<Product_data>() {
            @Override
            public void onResponse(Call<Product_data> call, Response<Product_data> response) {
                if (response.body().getConnection() == 1) {
                    if (response.body().getResult() == 1) {

                        productdatasList = response.body().getProductdata();

                        productAdapter = new Product_Adapter(false, productdatasList, getContext(), clicklistner);
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

    RecyclerClicklistner clicklistner = new RecyclerClicklistner() {
        @Override
        public void onLongClick(View view, Product_data.Productdatas productdatas) {
            PopupMenu popupMenu = new PopupMenu(getContext(), view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    if (item.getItemId() == R.id.popup_edit) {
                        List<Product_data.Productdatas> productdatasList = new ArrayList<>();
                        productdatasList.add(productdatas);
                        setFragment(new Add_Fragment(productdatasList));

                    } else if (item.getItemId() == R.id.popup_delete) {

                        System.out.println(productdatas.getID());
                        Retro.getInstance().retroAPI.Delete_Pro(productdatas.getID()).enqueue(new Callback<Delete_Products>() {
                            @Override
                            public void onResponse(Call<Delete_Products> call, Response<Delete_Products> response) {
                                System.out.println(response.body().getConnection());
                                System.out.println(response.body().getResult());
                                if (response.body().getConnection() == 1) {
                                    if (response.body().getResult() == 1) {
                                        for (int i = 0; i < productdatasList.size(); i++) {

                                            if (productdatasList.get(i).getID().equals(productdatas.getID())) {
                                                productdatasList.remove(i);
                                                productAdapter.notifyDataSetChanged();
                                                Toast.makeText(getContext(), "Delete Sucessfully!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Delete_Products> call, Throwable t) {
                                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                System.out.println(t.getLocalizedMessage());
                            }
                        });
                    }
                    return true;
                }
            });
            popupMenu.show();
        }
    };

    public void setFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout, fragment)
                .commit();
    }
}
