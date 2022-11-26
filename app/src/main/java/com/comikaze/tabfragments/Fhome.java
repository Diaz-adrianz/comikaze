package com.comikaze.tabfragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.comikaze.BacaActivity;
import com.comikaze.CreatorActivity;
import com.comikaze.R;
import com.comikaze.RiwayatActivity;
import com.comikaze.adapters.CardHorz;
import com.comikaze.helpers.LocalStorage;
import com.comikaze.models.ManList;
import com.comikaze.models.Mans;
import com.comikaze.models.UserPreference;
import com.comikaze.rest.Rest;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fhome extends Fragment {

    View v;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://fourtour.site/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Rest service = retrofit.create(Rest.class);

    ImageButton btn_tutupLanjut;
    CardView cardLanjut;
    RelativeLayout parentHome;
    LinearLayout topheader;

    private RecyclerView rv_trending, rv_populer, rv_random;
    private CardHorz adp_popular, adp_trending, adp_random;

    private SharedPreferences sp;
    private LocalStorage lokal;
    private ArrayList<UserPreference> riwayatBaca = new ArrayList<>();
    private ArrayList<Mans> trending, populer, random;

    public Fhome() {
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        rv_trending = v.findViewById(R.id.trending);
        rv_populer = v.findViewById(R.id.populer);
        rv_random = v.findViewById(R.id.random);

        parentHome = v.findViewById(R.id.parenthome);
        topheader = v.findViewById(R.id.header);
        cardLanjut = v.findViewById(R.id.card_lanjut);

        getDataHot();
        getDataPopular();
        getDataRandom();

        sp = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        lokal = new LocalStorage(sp, riwayatBaca, "riwayat");
        riwayatBaca = lokal.getRiwayatBaca();

        if ( riwayatBaca.size() > 0) {
            RelativeLayout lanjutBaca = v.findViewById(R.id.lanjutbaca);
            ImageButton nav_riwayat = v.findViewById(R.id.nav_riwayat);
            TextView lanjut_title = v.findViewById(R.id.lanjut_title);
            TextView lanjut_subtitle = v.findViewById(R.id.lanjut_chapter);

            String[] arrChap = String.valueOf(riwayatBaca.get(0).getSubtitle()).toLowerCase().split(" ");
            int indexChap = Arrays.asList(arrChap).indexOf("chapter");
            String strChap = String.join(" ", Arrays.copyOfRange(arrChap, indexChap, arrChap.length ));

            lanjut_title.setText(strChap);
            lanjut_subtitle.setText(riwayatBaca.get(0).getTitle());

            lanjutBaca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent go = new Intent(getContext(), BacaActivity.class);
                    go.putExtra("endpoint_chapter", riwayatBaca.get(0).getEndpoint());
                    go.putExtra("title", riwayatBaca.get(0).getTitle());

                    getContext().startActivity(go);
                }
            });

            nav_riwayat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent go = new Intent(getContext(), RiwayatActivity.class);
                    startActivity(go);
                }
            });
        } else {
            parentHome.removeView(cardLanjut);
        }


        btn_tutupLanjut = v.findViewById(R.id.tutup);
        btn_tutupLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentHome.removeView(cardLanjut);
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

    private void getDataHot() {
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Mengambil data", "Mohon tunggu..", false, false);
        Call<ManList> call = service.getManList("hot", "acak");
        call.enqueue(new Callback<ManList>() {
             @Override
             public void onResponse(Call<ManList> call, Response<ManList> response) {
                 try {

                     loading.dismiss();

                     if( response.body().getStatus()) {
                         trending = response.body().getMangaList();

                         adp_trending = new CardHorz(getContext(), trending.subList(0, 8));
                         rv_trending.setLayoutManager( new LinearLayoutManager(getContext().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                         rv_trending.setAdapter(adp_trending);
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

    private void getDataPopular() {
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Mengambil data", "Mohon tunggu..", false, false);
        Call<ManList> call = service.getManList("popular", "acak");
        call.enqueue(new Callback<ManList>() {
             @Override
             public void onResponse(Call<ManList> call, Response<ManList> response) {
                 try {

                     loading.dismiss();

                     if( response.body().getStatus()) {
                         populer = response.body().getMangaList();

                         adp_popular = new CardHorz(getContext(), populer.subList(0, 8));
                         rv_populer.setLayoutManager( new LinearLayoutManager(getContext().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                         rv_populer.setAdapter(adp_popular);
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

    private void getDataRandom() {
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Mengambil data", "Mohon tunggu..", false, false);
        Call<ManList> call = service.getManList("recommended", "acak");
        call.enqueue(new Callback<ManList>() {
             @Override
             public void onResponse(Call<ManList> call, Response<ManList> response) {
                 try {

                     loading.dismiss();

                     if( response.body().getStatus()) {
                         random = response.body().getMangaList();

                         adp_random = new CardHorz(getContext(), random.subList(0,8));
                         rv_random.setLayoutManager( new LinearLayoutManager(getContext().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                         rv_random.setAdapter(adp_random);
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
