package com.example.post_api.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.post_api.Models.Login_Data;
import com.example.post_api.R;
import com.example.post_api.Retro;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {

    EditText email, password;
    Button btnlogin;
    TextView ragister;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpassword);
        btnlogin = findViewById(R.id.btnlogin);
        ragister = findViewById(R.id.ragister);

        preferences = getSharedPreferences("Log_in_pref", MODE_PRIVATE);
        editor = preferences.edit();

        boolean login = preferences.getBoolean("login", false);

        if (login) {
            startActivity(new Intent(Login_Activity.this, MainActivity.class));
            finish();
        }

        ragister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, Ragister_Activity.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {

                    if (password.length() == 4) {

                        Retro.getInstance().retroAPI.Login_User(email.getText().toString(), password.getText().toString()).enqueue(new Callback<Login_Data>() {
                            @Override
                            public void onResponse(Call<Login_Data> call, Response<Login_Data> response) {

                                if (response.body().getConnection() == 1) {
                                    if (response.body().getResult() == 1) {

                                        editor.putBoolean("login", true);
                                        editor.putString("name", response.body().getUserdata().getName());
                                        editor.putString("Userid", response.body().getUserdata().getId());
                                        editor.commit();

                                        email.setText("");
                                        password.setText("");

                                        Toast.makeText(Login_Activity.this, "Login Succesfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Login_Activity.this, "Something went Wrong!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Login_Data> call, Throwable t) {
                                Toast.makeText(Login_Activity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    } else {
                        Toast.makeText(Login_Activity.this, "Passwoed Length is 4 Charcter!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login_Activity.this, "Eneter Proper Enput!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}