package com.example.tindernet.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

// Clases importadas para lo de las tarjetas
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tindernet.Company;
import com.example.tindernet.CompanyAdapter;
import com.example.tindernet.R;
import com.example.tindernet.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ViewPager2 viewPager;
    private CompanyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager = root.findViewById(R.id.viewPager);

        List<Company> companies = new ArrayList<>();
        companies.add(new Company(R.drawable.empresa1, "Description for Company 1"));
        companies.add(new Company(R.drawable.empresa2, "Description for Company 2"));
        // Añade más empresas según sea necesario

        adapter = new CompanyAdapter(companies);
        viewPager.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}