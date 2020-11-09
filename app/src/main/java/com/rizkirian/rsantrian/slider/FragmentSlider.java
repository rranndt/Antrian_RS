package com.rizkirian.rsantrian.slider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rizkirian.rsantrian.R;

import java.util.Objects;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class FragmentSlider extends Fragment {

    private static final String ARG_PARAM1 = "imgSlider";
    private String imageUrls;
    public FragmentSlider() {
    }

    public static FragmentSlider newInstance(String params) {
        FragmentSlider fragment = new FragmentSlider();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, params);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        imageUrls = getArguments().getString(ARG_PARAM1);
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        ImageView img = view.findViewById(R.id.img);
        Glide.with(Objects.requireNonNull(getActivity()))
                .load(imageUrls)
                .placeholder(R.drawable.white)
                .into(img);
        return view;
    }
}