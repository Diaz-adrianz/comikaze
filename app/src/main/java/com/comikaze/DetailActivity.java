package com.comikaze;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.comikaze.adapters.Card;
import com.comikaze.adapters.CardList;
import com.comikaze.helpers.JustifyListView;
import com.comikaze.helpers.LoadImageFromInternet;
import com.comikaze.helpers.LocalStorage;
import com.comikaze.models.Chapters;
import com.comikaze.models.Details;
import com.comikaze.models.Details_chapters;
import com.comikaze.models.UserPreference;
import com.comikaze.rest.Rest;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    ImageButton btnBack, btnSimpan, btnFav;
    ImageView thumbView;
    Button typeView, ageView, statusView, authorView, mulaiBaca;
    TextView titleView, genreView, synopsisView;
    RecyclerView rv_chapters;

    private Card adp_chapters;
    private SharedPreferences sp;
    private ArrayList<Details_chapters> chapters;
    private ArrayList<UserPreference> chapters2 = new ArrayList<>(), simpananKomik = new ArrayList<>(), favoritKomik = new ArrayList<>();
    private String endpoint, title;
    private LocalStorage lokalSuka, lokalSimpan;
    private boolean liked = false, saved = false;
    private int likedPos, savedPos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();

        setContentView(R.layout.activity_detail);

        sp = getSharedPreferences("user", MODE_PRIVATE);

// AMBIL VIEW
        thumbView = findViewById(R.id.mangaimage);
        typeView = findViewById(R.id.type);
        ageView = findViewById(R.id.age);
        statusView = findViewById(R.id.status);
        authorView = findViewById(R.id.author);
        titleView = findViewById(R.id.title);
        genreView = findViewById(R.id.genre);
        synopsisView = findViewById(R.id.sinopsis);
        rv_chapters = findViewById(R.id.rv_chapters);

        btnBack = findViewById(R.id.btn_back);
        mulaiBaca = findViewById(R.id.mulaibaca);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnFav = findViewById(R.id.btn_fav);

// AMBIL DATA EXTRA INTENT
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                endpoint = null;
            } else {
                endpoint = extras.getString("endpoint");
            }
        } else {
            endpoint = (String) savedInstanceState.getSerializable("endpoint");
        }

// DAPATKAN & TAMPILKAN DATA
        getData();


// TOMBOL KEMBALI
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

// MULAI BACA
        mulaiBaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getApplicationContext(), BacaActivity.class);
                go.putExtra("endpoint_chapter", chapters.get(chapters.size() - 1).getChapterEndpoint());
                go.putExtra("title", String.valueOf(titleView.getText()));

                startActivity(go);
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saved) {
                    simpananKomik.remove(savedPos);
                    btnSimpan.setColorFilter(ContextCompat.getColor(DetailActivity.this, R.color.gray));
                    Toast.makeText(getApplicationContext(), "Komik dihapus dari simpanan", Toast.LENGTH_SHORT).show();
                } else {
                    simpananKomik.add(0, new UserPreference(title, String.valueOf(typeView.getText()), endpoint));
                    btnSimpan.setColorFilter(ContextCompat.getColor(DetailActivity.this, R.color.accent));
                    Toast.makeText(getApplicationContext(), "Komik ditambahkan ke simpanan", Toast.LENGTH_SHORT).show();
                }

                lokalSimpan.setRiwayatBaca(simpananKomik);
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (liked) {
                    favoritKomik.remove(likedPos);
                    btnFav.setColorFilter(ContextCompat.getColor(DetailActivity.this, R.color.gray));
                    Toast.makeText(getApplicationContext(), "Komik dihapus dari favorit", Toast.LENGTH_SHORT).show();
                } else {
                    favoritKomik.add(0, new UserPreference(title, String.valueOf(typeView.getText()), endpoint));
                    btnFav.setColorFilter(ContextCompat.getColor(DetailActivity.this, R.color.accent));
                    Toast.makeText(getApplicationContext(), "Komik ditambahkan ke favorit", Toast.LENGTH_SHORT).show();
                }

                lokalSuka.setRiwayatBaca(favoritKomik);
            }
        });

    }

    private void checkLocal() {
        lokalSimpan = new LocalStorage(sp, simpananKomik, "simpanan");
        simpananKomik = lokalSimpan.getRiwayatBaca();

        for (int i = 0; i < simpananKomik.size(); i++ ) {
            if ( simpananKomik.get(i).getTitle().equals(title)) {
                saved = true;
                savedPos = i;
            }
        }

        lokalSuka = new LocalStorage(sp, favoritKomik, "favorit");
        favoritKomik = lokalSuka.getRiwayatBaca();

        for (int i = 0; i < favoritKomik.size(); i++ ) {
            if ( favoritKomik.get(i).getTitle().equals(title) ) {
                liked = true;
                likedPos = i;
            }
        }

        if (saved) btnSimpan.setColorFilter(ContextCompat.getColor(this, R.color.accent));
        if (liked) btnFav.setColorFilter(ContextCompat.getColor(this, R.color.accent));

    }

    private void getData() {
        final String ROOT_URL = "https://fourtour.site/";
        final ProgressDialog loading = ProgressDialog.show(this, "Mengambil data", "Mohon tunggu..", false, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Rest service = retrofit.create(Rest.class);
        Call<Details> call = service.getDetails(endpoint);
        call.enqueue(new Callback<Details>() {
             @SuppressLint("SetTextI18n")
             @Override
             public void onResponse(Call<Details> call, Response<Details> response) {
                 try {

                     loading.dismiss();

                     if( response.code() == 200 && !Objects.equals(response.body().getTitle(), "")) {
                         new LoadImageFromInternet(thumbView).execute(String.valueOf(response.body().getThumb()));
                         titleView.setText(response.body().getTitle());
                         typeView.setText(response.body().getType());
                         ageView.setText(response.body().getAge());
                         statusView.setText(response.body().getStatus());
                         authorView.setText(response.body().getAuthor());
                         synopsisView.setText(response.body().getSynopsis().replaceAll("\t", ""));
                         genreView.setText(String.join(", ", response.body().getGenreList()));
                         chapters =  response.body().getChapter();

                         for (int i = 0; i < chapters.size(); i++) {
                             chapters2.add(new UserPreference( response.body().getTitle(), chapters.get(i).getChapterTitle(), chapters.get(i).getChapterEndpoint()));
                         }

                         title = response.body().getTitle();

                         adp_chapters = new Card(DetailActivity.this, chapters2, "chapter");
                         rv_chapters.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                         rv_chapters.setAdapter(adp_chapters);

                         checkLocal();
                     } else {
                         Toast.makeText(getApplicationContext(), "Gagal mengambil data :(", Toast.LENGTH_SHORT).show();
                     }


                 } catch (Exception e) {
                     Toast.makeText(getApplicationContext(), "Gagal mengambil data :(", Toast.LENGTH_SHORT).show();
                     e.printStackTrace();
                 }
             }

             @Override
             public void onFailure(Call<Details> call, Throwable t) {
                 loading.dismiss();
                 Toast.makeText(getApplicationContext(), "Gagal mengambil data :(", Toast.LENGTH_SHORT).show();
             }

         }
        );
    }
}