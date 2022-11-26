package com.comikaze.tabfragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comikaze.CreatorActivity;
import com.comikaze.R;
import com.comikaze.adapters.CardHorz;
import com.comikaze.adapters.CardVert;
import com.comikaze.models.ManList;
import com.comikaze.models.Mans;
import com.comikaze.rest.Rest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fsearch extends Fragment {

    View v;

    EditText kolomCari;
    LinearLayout topheader;

    private RecyclerView rv_hasil;
    private LinearLayoutManager layoutManager;
    private CardVert adp_hasil;

    private ArrayList<Mans> komiks;

    public Fsearch() {
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search, container, false);

        layoutManager = new LinearLayoutManager(getContext().getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        topheader = v.findViewById(R.id.header);
        rv_hasil = v.findViewById(R.id.hasil);
        kolomCari = v.findViewById(R.id.kolomcari);
        kolomCari.setImeActionLabel("Cari", KeyEvent.KEYCODE_ENTER);
        kolomCari.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String value = kolomCari.getText().toString();

                    if (value != "") {
                        getData(value);
                    }

                    return true;
                }
                return false;
            }
        });

        topheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getContext(), CreatorActivity.class);
                startActivity(go);
            }
        });

        return v;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void getData(String value) {
        final String ROOT_URL = "https://fourtour.site/";
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Mengambil data", "Mohon tunggu..", false, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Rest service = retrofit.create(Rest.class);
        Call<ManList> call = service.getManList("search", value);
        call.enqueue(new Callback<ManList>() {
             @SuppressLint("SetTextI18n")
             @Override
             public void onResponse(Call<ManList> call, Response<ManList> response) {
                 try {

                     loading.dismiss();

                     if( response.body().getStatus()) {
                         komiks = response.body().getMangaList();

                         adp_hasil = new CardVert(getContext(), komiks);
                         rv_hasil.setLayoutManager(layoutManager);
                         rv_hasil.setAdapter(adp_hasil);
                     } else {
                         Toast.makeText(getContext(), "Gagal mengambil data :(", Toast.LENGTH_SHORT).show();
                     }


                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }

             @Override
             public void onFailure(Call<ManList> call, Throwable t) {
                 loading.dismiss();

                 Toast.makeText(getContext(), "Gagal mengambil data :(", Toast.LENGTH_SHORT).show();
             }

         }
        );
    }
}
