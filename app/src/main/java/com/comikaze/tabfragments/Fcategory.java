package com.comikaze.tabfragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comikaze.CreatorActivity;
import com.comikaze.R;
import com.comikaze.adapters.CardVert;
import com.comikaze.models.GenreList;
import com.comikaze.models.Genres;
import com.comikaze.models.ManList;
import com.comikaze.models.Mans;
import com.comikaze.rest.Rest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fcategory extends Fragment {
    View v;
    Spinner spinner_type, spinner_genre;
    CardView btn_search;
    LinearLayout topheader;

    private String[] types;
    private ArrayList<String> genres = new ArrayList<>();
    private ArrayList<Mans> comics = new ArrayList<>();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://fourtour.site/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private Rest service = retrofit.create(Rest.class);
    private CardVert adp_hasil;

    private LinearLayoutManager layoutManager;
    private RecyclerView rv_hasil;
    private ArrayAdapter adapter_types, adapter_genres;

    public Fcategory() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_category, container, false);

        layoutManager = new LinearLayoutManager(getContext().getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        topheader = v.findViewById(R.id.header);
        rv_hasil = v.findViewById(R.id.hasil);
        spinner_type = (Spinner) v.findViewById(R.id.spinner_type);
        spinner_genre = (Spinner) v.findViewById(R.id.spinner_genre);
        btn_search = v.findViewById(R.id.btn_search);

        types = new String[]{"Manga", "Manhwa", "Manhua"};
        adapter_types = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, types);
        adapter_types.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_type.setAdapter(adapter_types);

        getDataGenre();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String okType = spinner_type.getSelectedItem().toString();
                String okGenre = spinner_genre.getSelectedItem().toString();

                getData(okGenre, okType);
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

    private void getDataGenre() {
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Mengambil data", "Mohon tunggu..", false, false);
        Call<GenreList> call = service.getGenres();
        call.enqueue(new Callback<GenreList>() {
             @Override
             public void onResponse(Call<GenreList> call, Response<GenreList> response) {
                 try {

                     loading.dismiss();

                     if( response.body().getStatus()) {
                         for (int i = 0; i < response.body().getListGenre().size(); i++) {
                             genres.add(response.body().getListGenre().get(i).getGenreName());
                         }

                         adapter_genres = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, genres);
                         adapter_genres.setDropDownViewResource(android.R.layout.simple_spinner_item);
                         spinner_genre.setAdapter(adapter_genres);
                     } else {
                         Toast.makeText(getContext(), "Gagal mengambil data :(", Toast.LENGTH_SHORT).show();
                     }


                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }

             @Override
             public void onFailure(Call<GenreList> call, Throwable t) {
                 Toast.makeText(getContext(), "Gagal mengambil data :(", Toast.LENGTH_SHORT).show();
             }

         }
        );
    }

    private void getData(String reqGenre, String comic ) {
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Mengambil data", "Mohon tunggu..", false, false);
        Call<ManList> call = service.getManListByGenre(reqGenre, comic);
        call.enqueue(new Callback<ManList>() {
             @SuppressLint("SetTextI18n")
             @Override
             public void onResponse(Call<ManList> call, Response<ManList> response) {
                 try {

                     loading.dismiss();

                     if( response.code() == 200) {
                         comics = response.body().getMangaList();

                         Log.e("Size", String.valueOf(comics.size()));
                         adp_hasil = new CardVert(getContext(), comics);
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
                 Toast.makeText(getContext(), "Gagal mengambil data :(", Toast.LENGTH_SHORT).show();
             }

         }
        );
    }
}

