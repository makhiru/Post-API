package com.example.post_api.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.post_api.Frgament.Add_Fragment;
import com.example.post_api.Frgament.MyProduct_Fragment;
import com.example.post_api.Frgament.ShowAll_Fragment;
import com.example.post_api.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    NavigationView navigation;
    Toolbar toolbar;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);

        preferences = getSharedPreferences("Log_in_pref", MODE_PRIVATE);
        editor = preferences.edit();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar, R.string.open, R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigation.setCheckedItem(R.id.add);
        setFragment(new ShowAll_Fragment());
        toolbar.setTitle("Add Product");

        View headerview = navigation.getHeaderView(0);
        TextView drawer_name = headerview.findViewById(R.id.username);
        drawer_name.setText("" + preferences.getString("name", "1"));

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.logout) {
                    editor.putBoolean("login", false);
                    editor.commit();

                    startActivity(new Intent(MainActivity.this, Login_Activity.class));
                    finish();

                } else if (item.getItemId() == R.id.add) {
                    toolbar.setTitle("Add Product");
                    setFragment(new Add_Fragment());

                } else if (item.getItemId() == R.id.myprod) {
                    toolbar.setTitle("My Product");
                    setFragment(new MyProduct_Fragment());

                } else if (item.getItemId() == R.id.show) {
                    toolbar.setTitle("Show Product");
                    setFragment(new ShowAll_Fragment());

                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}