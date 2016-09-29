package com.example.jere.ecomorganics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerlayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;


    NavigationView navigationView;

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       //getSupportActionBar().setElevation(2f);

        drawerlayout =(DrawerLayout)findViewById(R.id.drawer_layout);

        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerlayout.setDrawerListener(actionBarDrawerToggle);

        fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container,new HomeFragment());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("CATEGORIES");

        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_id:
                        fragmentTransaction =getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new HomeFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("CATEGORIES");
                        item.setChecked(true);
                        drawerlayout.closeDrawers();
                    break;
                    case R.id.Login:
                        fragmentTransaction= getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new LoginFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Login In");
                        item.setChecked(true);
                        drawerlayout.closeDrawers();
                        break;
                    case R.id.register:
                        fragmentTransaction= getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new RegisterFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Create an Account");
                        item.setChecked(true);
                        drawerlayout.closeDrawers();
                        break;
                    case R.id.setting:
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new SettingFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("SETTINGS");
                        item.setChecked(true);
                        drawerlayout.closeDrawers();
                        break;
                    case R.id.cart:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new CartFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("SHOPPING CART ITEMS");
                        item.setChecked(true);
                        drawerlayout.closeDrawers();
                        break;
                    case R.id.orders:
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new OrderFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("View Orders");
                        item.setChecked(true);
                        drawerlayout.closeDrawers();
                        break;
                    case R.id.share:
                        fragmentTransaction =getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new ShareFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Share");
                        item.setChecked(true);
                        drawerlayout.closeDrawers();
                        break;
                }
                return true;
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}
