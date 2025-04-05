package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,0,0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            selectDrawerItem(item);
            return true;
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new StandardCalculatorFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_standard);
            setTitle(navigationView.getMenu().findItem(R.id.nav_standard).getTitle());
        }
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        Class<? extends Fragment> fragmentClass;

        int itemId = item.getItemId();
        if (itemId == R.id.nav_standard) {
            fragmentClass = StandardCalculatorFragment.class;
        } else if (itemId == R.id.nav_programmer) {
            fragmentClass = ProgrammingCalculatorFragment.class;
        } else {
            fragmentClass = StandardCalculatorFragment.class;
        }

        try {
            fragment = fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            item.setChecked(true);
            setTitle(item.getTitle());
        }

        drawerLayout.closeDrawer(GravityCompat.START);
    }


}