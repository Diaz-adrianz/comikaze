package com.comikaze.tabfragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.comikaze.FavoritActivity;
import com.comikaze.R;
import com.comikaze.RiwayatActivity;
import com.comikaze.SimpanActivity;

public class Fprofile extends Fragment {
    View v;

    private Button nav_riwayat, nav_simpan, nav_favorit, nav_dibagikan;

    public Fprofile() {
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);

        nav_riwayat = v.findViewById(R.id.nav_riwayat);
        nav_simpan = v.findViewById(R.id.nav_simpan);
        nav_favorit = v.findViewById(R.id.nav_favorite);
        nav_dibagikan = v.findViewById(R.id.nav_share);

        nav_riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getContext(), RiwayatActivity.class);
                startActivity(go);
            }
        });

        nav_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getContext(), SimpanActivity.class);
                startActivity(go);
            }
        });

        nav_favorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getContext(), FavoritActivity.class);
                startActivity(go);
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
