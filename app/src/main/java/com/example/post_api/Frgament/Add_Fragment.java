package com.example.post_api.Frgament;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Base64OutputStream;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.post_api.Models.Delete_Products;
import com.example.post_api.Models.Login_Data;
import com.example.post_api.Models.Product;
import com.example.post_api.Models.Product_data;
import com.example.post_api.R;
import com.example.post_api.Retro;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Fragment extends Fragment {

    EditText name, price, description;
    ImageView image;
    Button btnadd;

    Uri pro_uri = Uri.parse("");
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    List<Product_data.Productdatas> productdatasList = new ArrayList<>();

    public Add_Fragment(List<Product_data.Productdatas> productdatasList) {
        this.productdatasList = productdatasList;
    }

    public Add_Fragment() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        name = view.findViewById(R.id.pro_name);
        price = view.findViewById(R.id.pro_price);
        description = view.findViewById(R.id.pro_des);
        image = view.findViewById(R.id.pro_img);
        btnadd = view.findViewById(R.id.btnadd);

        preferences = getActivity().getSharedPreferences("Log_in_pref", MODE_PRIVATE);
        editor = preferences.edit();

        if (!productdatasList.isEmpty()) {
            btnadd.setText("Update");
            name.setText("" + productdatasList.get(0).getPRO_NAME());
            price.setText("" + productdatasList.get(0).getPRO_PRICE());
            description.setText("" + productdatasList.get(0).getPRO_DES());
            pro_uri = Uri.parse("1");
            String url = "https://hetsweb.000webhostapp.com/myApp/" + productdatasList.get(0).getPRO_IMAGE();
            Picasso.get().load(url).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.image_placeholder).into(image);
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 801);
                }
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!name.getText().toString().isEmpty()
                        && !price.getText().toString().isEmpty()
                        && !description.getText().toString().isEmpty()
                        && !pro_uri.toString().isEmpty()) {

                    String proname = name.getText().toString();
                    String proprice = price.getText().toString();
                    String prodes = description.getText().toString();
                    String userid = preferences.getString("Userid", "1");
                    String img = getImagefromView(image);

                    if (!productdatasList.isEmpty()) {

                        Retro.getInstance().retroAPI.Update_pro(productdatasList.get(0).getID(), proname, proprice, prodes, img, productdatasList.get(0).getPRO_IMAGE()).enqueue(new Callback<Delete_Products>() {
                            @Override
                            public void onResponse(Call<Delete_Products> call, Response<Delete_Products> response) {

                                System.out.println(response.body().getConnection());
                                System.out.println(response.body().getResult());
                                if (response.body().getConnection() == 1) {
                                    if (response.body().getResult() == 1) {

                                        Toast.makeText(getContext(), "Product updated", Toast.LENGTH_SHORT).show();
                                        setFragment(new ShowAll_Fragment());
                                    } else {
                                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Delete_Products> call, Throwable t) {
                                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                System.out.println(t.getLocalizedMessage());
                            }
                        });

                    } else {
                        Retro.getInstance().retroAPI.Add_Product(userid, proname, proprice, prodes, img).enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                if (response.body().getConnection() == 1) {
                                    if (response.body().getProductaddd() == 1) {

                                        image.setImageResource(R.drawable.addimg);
                                        pro_uri = Uri.parse("");
                                        name.setText("");
                                        price.setText("");
                                        description.setText("");

                                        Toast.makeText(getContext(), "Product Added!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Someting went Wrong!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable t) {
                                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                } else {
                    if (name.getText().toString().isEmpty()) {
                        name.setError("Enter Proper Enput!");
                    }
                    if (price.getText().toString().isEmpty()) {
                        price.setError("Enter Proper Enput!");
                    }
                    if (description.getText().toString().isEmpty()) {
                        description.setError("Enter Proper Enput!");
                    }
                }
            }
        });

        return view;
    }

    private String getImagefromView(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] bytes = baos.toByteArray();
        String imagedata = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            imagedata = Base64.getEncoder().encodeToString(bytes);
        }
        return imagedata;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 801) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 2001);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == 2001) {
                Uri uri = data.getData();
                if (uri != null) {
                    CropImage.activity(uri)
                            .start(getContext(), this);
                }
            }

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                pro_uri = result.getUri();
                image.setImageURI(pro_uri);
            }
        }

    }

    public void setFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout, fragment)
                .commit();
    }
}