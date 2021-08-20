package com.rumahku.rumahku.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.rumahku.rumahku.R;
import com.rumahku.rumahku.databinding.ActivityHomeDetailBinding;

import java.util.List;

public class HomeDetail extends AppCompatActivity {

    public static final String EXTRA_HOME = "extra_home";
    private ActivityHomeDetailBinding binding;

    private String schools = "";
    private String hospitals = "";
    private String shoppings = "";
    private String recreations = "";


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        HomeModel model = getIntent().getParcelableExtra(EXTRA_HOME);
        String title = model.getTitle();
        String location = model.getLocation();
        String latlng = model.getLatlng();
        List<String> school = model.getSchool();
        List<String> hospital = model.getHospital();
        List<String> shopping = model.getShopping();
        List<String> recreation = model.getRecreation();


        binding.title.setText(title);
        binding.location.setText(location);
        binding.latlng.setText("Posisi: " + latlng);

        Glide.with(this)
                .load(R.drawable.logo)
                .into(binding.imageView2);




        for(int i = 0; i< school.size(); i++) {
            if(i==0) {
                schools += "- " + school.get(i);
            } else {
                schools = schools + "\n- " + school.get(i);
            }
        }

        for(int i = 0; i< hospital.size(); i++) {
            if(i==0) {
                hospitals += "- " + hospital.get(i);
            } else {
                hospitals = hospitals + "\n- " + hospital.get(i);
            }
        }

        for(int i = 0; i< shopping.size(); i++) {
            if(i==0) {
                shoppings += "- " + shopping.get(i);
            } else {
                shoppings = shoppings + "\n- " + shopping.get(i);
            }
        }

        for(int i = 0; i< recreation.size(); i++) {
            if(i==0) {
                recreations += "- " + recreation.get(i);
            } else {
                recreations = recreations + "\n- " + recreation.get(i);
            }
        }

        binding.school.setText(schools);
        binding.hospital.setText(hospitals);
        binding.shopping.setText(shoppings);
        binding.recreation.setText(recreations);

        binding.imageView.setOnClickListener(view -> onBackPressed());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}