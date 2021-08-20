package com.rumahku.rumahku;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.rumahku.rumahku.databinding.ActivityHomeBinding;
import com.rumahku.rumahku.ui.about.AboutFragment;
import com.rumahku.rumahku.ui.home.HomeFragment;
import com.rumahku.rumahku.ui.maps.MapsFragment;

public class HomeActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.rumahku.rumahku.databinding.ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // MEMBUAT NAVIGASI SUPAYA TERDAPAT 3 NAVIGASI YANG TERDIRI DARI NAVIGASI MAPS, NAVIGASI PERUMAHAN, DAN NAVIGASI TENTANG APLIKASI
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = new MapsFragment();
            switch (item.getItemId()) {
                case R.id.navigation_maps: {
                    navView.getMenu().findItem(R.id.navigation_maps).setEnabled(false);
                    navView.getMenu().findItem(R.id.navigation_home).setEnabled(true);
                    navView.getMenu().findItem(R.id.navigation_info).setEnabled(true);
                    selectedFragment = new MapsFragment();
                    break;
                }

                case R.id.navigation_home: {
                    navView.getMenu().findItem(R.id.navigation_maps).setEnabled(true);
                    navView.getMenu().findItem(R.id.navigation_home).setEnabled(false);
                    navView.getMenu().findItem(R.id.navigation_info).setEnabled(true);
                    selectedFragment = new HomeFragment();
                    break;
                }
                case R.id.navigation_info: {
                    navView.getMenu().findItem(R.id.navigation_maps).setEnabled(true);
                    navView.getMenu().findItem(R.id.navigation_home).setEnabled(true);
                    navView.getMenu().findItem(R.id.navigation_info).setEnabled(false);
                    selectedFragment = new AboutFragment();
                    break;
                }
                default: {
                    navView.getMenu().findItem(R.id.navigation_maps).setEnabled(false);
                }
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, selectedFragment);
            transaction.commit();
            return true;
        });

    }

}