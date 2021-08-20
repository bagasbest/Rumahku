package com.rumahku.rumahku.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.rumahku.rumahku.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);


        initRecyclerView();
        initViewModel();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        binding.rvHome.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HomeAdapter();
        adapter.notifyDataSetChanged();
        binding.rvHome.setAdapter(adapter);
    }

    private void initViewModel() {
        // tampilkan daftar perumahan
        HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

       viewModel.setAllHome();
        viewModel.getHome().observe(getViewLifecycleOwner(), hospital -> {
            if (hospital.size() > 0) {
                adapter.setData(hospital);
            }
            binding.progressBar.setVisibility(View.GONE);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}