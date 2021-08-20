package com.rumahku.rumahku.ui.maps;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.rumahku.rumahku.R;
import com.rumahku.rumahku.databinding.FragmentMapsBinding;

import org.jetbrains.annotations.NotNull;

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
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Posisi Anda Saat Ini");
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