package com.rumahku.rumahku.ui.maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.rumahku.rumahku.R;
import com.rumahku.rumahku.databinding.FragmentMapsBinding;
import com.rumahku.rumahku.ui.home.HomeDetail;
import com.rumahku.rumahku.ui.home.HomeModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapsBinding binding;
    private Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int permissionCode = 101;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMapsBinding.inflate(inflater, container, false);

        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(getActivity());


        // UNTUK MENGETAHUI LOKASI PENGGUNA SAAT INI
        fetchLocation();

        // MELAKUKAN REQUEST AKTIFKAN GPS OTOMATIS KEPADA PERANGKAT PENGGUNA
        Maps.showLocationPrompt(this);



        return binding.getRoot();
    }

    private void fetchLocation() {
        // MENGECEK APAKAH PENGGUNA SUDAH MENYALAKAN PERMISSION LOKASI ATAU BELUM, JIKA BELUM MAKA TAMPILKAN PERMISSION LOKASI, UNTUK MENDETEKSI LOKASI SAAT INI
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    permissionCode
            );
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if(location != null) {
                currentLocation = location;

                SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_maps);
                assert supportMapFragment != null;
                supportMapFragment.getMapAsync(this);
            }
        });
    }

    // Untuk menghapus tumpukan activity jika tidak digunakan lagi
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Hai, Ini merupakan lokasi kamu");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        googleMap.addMarker(markerOptions);
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

        LatLng tamanSentosa = new LatLng(-6.319563, 107.134485);
        googleMap.addMarker(new MarkerOptions().position(tamanSentosa).title("TAMAN SENTOSA").snippet("Sentosa Raya, Lemah Abang, 17550, Jawa Barat"));

        LatLng villaMC = new LatLng(-6.342736,107.119977);
        googleMap.addMarker(new MarkerOptions().position(villaMC).title("VILLA MUTIARA CIKARANG").snippet("Raya Cibarusah, Cibarusah, 17520, Jawa Barat"));

        LatLng grahaAC = new LatLng(-6.278229,107.184665);
        googleMap.addMarker(new MarkerOptions().position(grahaAC).title("GRAHA ASRI CIKARANG").snippet("Cikarang, Bekasi, Jawa Barat"));

        LatLng centralPark = new LatLng(-6.260217,107.172982);
        googleMap.addMarker(new MarkerOptions().position(centralPark).title("CENTRAL PARK CIKARANG").snippet("Jl. Urip Sumoharjo, 17530, Jawa Barat"));

        LatLng cikarangBaru = new LatLng(-6.279681,107.166570);
        googleMap.addMarker(new MarkerOptions().position(cikarangBaru).title("CIKARANG BARU").snippet("Jl. Kedasih Raya, Jababeka, kabupaten bekasi"));


        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(@NonNull @NotNull Marker marker) {
                return null;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public View getInfoContents(@NonNull @NotNull Marker marker) {
                LinearLayout info = new LinearLayout(getContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.LEFT);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(getContext());
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                Button button = new Button(getContext());
                button.setWidth(200);
                button.setHeight(50);
                button.setText("Info Selengkapnya");
                button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.primary));
                button.setAllCaps(false);

                info.addView(title);
                info.addView(snippet);
                info.addView(button);

                return info;
            }
        });

        googleMap.setOnInfoWindowClickListener(marker -> {
            if(marker.getTitle().equals("TAMAN SENTOSA")) {
                getDetailLocation("TAMAN SENTOSA");
            }
            else if(marker.getTitle().equals("VILLA MUTIARA CIKARANG")) {
                getDetailLocation("VILLA MUTIARA CIKARANG");
            }
            else if(marker.getTitle().equals("GRAHA ASRI CIKARANG")) {
                getDetailLocation("GRAHA ASRI CIKARANG");
            }
            else if(marker.getTitle().equals("CENTRAL PARK CIKARANG")) {
                getDetailLocation("CENTRAL PARK CIKARANG");
            }
            else if(marker.getTitle().equals("CIKARANG BARU")) {
                getDetailLocation("CIKARANG BARU");
            }
        });

    }

    private void getDetailLocation(String location) {
        final ArrayList<HomeModel> homeModelArrayList = new ArrayList<>();
        HomeModel model = new HomeModel();

        FirebaseFirestore
                .getInstance()
                .collection("home")
                .whereEqualTo("title", location)
                .get()
                .addOnCompleteListener(task -> {
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        List<String> hospitals = (List<String>) document.get("hospital");
                        model.setHospital(hospitals);
                        model.setLatlng("" + document.get("latlng"));
                        model.setLocation("" + document.get("location"));
                        List<String> recreation = (List<String>) document.get("recreation");
                        model.setRecreation(recreation);
                        List<String> school = (List<String>) document.get("school");
                        model.setSchool(school);
                        List<String> shopping = (List<String>) document.get("shopping");
                        model.setShopping(shopping);
                        model.setTitle("" + document.get("title"));

                        homeModelArrayList.add(model);
                    }

                    Intent intent = new Intent(getActivity(), HomeDetail.class);
                    intent.putExtra(HomeDetail.EXTRA_HOME, model);
                    startActivity(intent);

                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Gagal mengambil detail perumahan", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == permissionCode) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                fetchLocation();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LocationRequest.PRIORITY_HIGH_ACCURACY) {
            if(resultCode == Activity.RESULT_OK) {
                Log.e("Status", "On");
            } else {
                Log.e("Status", "Off");
            }
        }
    }
}