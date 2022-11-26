package com.comikaze;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comikaze.adapters.Halaman;
import com.comikaze.helpers.LocalStorage;
import com.comikaze.models.Chapters;
import com.comikaze.models.UserPreference;
import com.comikaze.rest.Rest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BacaActivity extends AppCompatActivity {

    private ImageButton btn_prev, btn_next, btn_back;
    private TextView titleView ;
    private RecyclerView images;
    private SharedPreferences sp;
    private String endpointChapter, endpointNext, endpointPrev, title, chapter_name;

    private ArrayList<UserPreference> riwayatBaca = new ArrayList<>(), riwayatBacaBaru = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        setContentView(R.layout.activity_baca);

        images = findViewById(R.id.images);
        titleView = findViewById(R.id.title);
        btn_next = findViewById(R.id.page_next);
        btn_prev = findViewById(R.id.page_prev);
        btn_back = findViewById(R.id.btn_back);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                endpointChapter = null;
                title = null;
            } else {
                endpointChapter = extras.getString("endpoint_chapter");
                title = extras.getString("title");
            }
        } else {
            endpointChapter = (String) savedInstanceState.getSerializable("endpoint_chapter");
            title = (String) savedInstanceState.getSerializable("title");
        }

        getData();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getApplicationContext(), BacaActivity.class);
                go.putExtra("endpoint_chapter", endpointPrev);
                go.putExtra("title", String.valueOf(title));

                startActivity(go);
                finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getApplicationContext(), BacaActivity.class);
                go.putExtra("endpoint_chapter", endpointNext);
                go.putExtra("title", String.valueOf(title));

                startActivity(go);
                finish();
            }
        });
    }

    private void setHistory() {
        sp = this.getSharedPreferences("user", MODE_PRIVATE);
        LocalStorage lokal = new LocalStorage(sp, riwayatBaca, "riwayat");
        riwayatBaca = lokal.getRiwayatBaca();

        for (int i = 0; i < riwayatBaca.size(); i++ ) {
            if ( !riwayatBaca.get(i).getTitle().equals(title) ) {
                riwayatBacaBaru.add(riwayatBaca.get(i));
            }
        }

        riwayatBacaBaru.add(0, new UserPreference(title, chapter_name, endpointChapter));

        lokal.setRiwayatBaca(riwayatBacaBaru);
    }

    private void getData() {
        final String ROOT_URL = "https://fourtour.site/";
        final ProgressDialog loading = ProgressDialog.show(this, "Mengambil data", "Mohon tunggu..", false, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Rest service = retrofit.create(Rest.class);
        Call<Chapters> call = service.getChapter(endpointChapter);
        call.enqueue(new Callback<Chapters>() {
                 @SuppressLint("SetTextI18n")
             @Override
             public void onResponse(Call<Chapters> call, Response<Chapters> response) {
                 try {
                     loading.dismiss();

                     if (response.code() == 200 && response.body() != null) {
                         titleView.setText(response.body().getChapterName());

                         endpointNext = response.body().getChapterNextEndpoint();
                         endpointPrev = response.body().getChapterPrevEndpoint();
                         chapter_name = response.body().getChapterName();

                         Halaman adapter = new Halaman(getApplicationContext(), response.body().getChapterImage());
                         images.setAdapter(adapter);
                         images.setLayoutManager(new LinearLayoutManager(BacaActivity.this));

                         setHistory();
                     } else {
                         Toast.makeText(getApplicationContext(), "Gagal mengambil data :(", Toast.LENGTH_SHORT).show();
                     }

                 } catch (Exception e) {
                     Toast.makeText(getApplicationContext(), "Gagal mengambil data :(", Toast.LENGTH_SHORT).show();
                     e.printStackTrace();
                 }
             }

             @Override
             public void onFailure(Call<Chapters> call, Throwable t) {
                 loading.dismiss();
                 Toast.makeText(getApplicationContext(), "Gagal mengambil data :(", Toast.LENGTH_SHORT).show();
             }

         }
        );
    }
}
