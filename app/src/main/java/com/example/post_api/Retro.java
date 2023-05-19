package com.example.post_api;

import com.example.post_api.Models.Delete_Products;
import com.example.post_api.Models.Login_Data;
import com.example.post_api.Models.Product;
import com.example.post_api.Models.Product_data;
import com.example.post_api.Models.Ragister_Data;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class Retro {
    String baseURL = "https://hetsweb.000webhostapp.com/YouApp/";
    public static Retro retro;
    public RetroAPI retroAPI;

    public Retro() {
        Retrofit Retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retroAPI = Retrofit.create(RetroAPI.class);
    }

    public static Retro getInstance() {
        if (retro == null) {
            retro = new Retro();
        }
        return retro;
    }

    public interface RetroAPI {
        @FormUrlEncoded
        @POST("register.php")
        Call<Ragister_Data> Ragister_User(@Field("name") String name, @Field("email") String email,
                                          @Field("password") String password);

        @FormUrlEncoded
        @POST("login.php")
        Call<Login_Data> Login_User(@Field("email") String email, @Field("password") String password);

        @FormUrlEncoded
        @POST("addProduct.php")
        Call<Product> Add_Product(@Field("userid") String userid, @Field("pname") String pname,
                                  @Field("pprize") String pprice, @Field("pdes") String pdes,
                                  @Field("productimage") String pimage);

        @FormUrlEncoded
        @POST("viewData.php")
        Call<Product_data> Product_Data(@Field("userid") String uid);

        @POST("viewalldata.php")
        Call<Product_data> ALL_Product_Data();

        @FormUrlEncoded
        @POST("deleteproduct.php")
        Call<Delete_Products> Delete_Pro(@Field("id") String id);

        @FormUrlEncoded
        @POST("updateproduct.php")
        Call<Delete_Products> Update_pro(@Field("id") String id, @Field("name") String name,
                                         @Field("price") String price, @Field("description") String desc,
                                         @Field("imagedata") String image, @Field("imagename") String ingname);

    }
}
