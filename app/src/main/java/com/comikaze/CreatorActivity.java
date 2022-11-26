package com.comikaze;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class CreatorActivity extends AppCompatActivity {

    private ImageButton btn_back;
    private Button btn_new;
    private RecyclerView rv_mine;
    private CardVert adp_hasil;

    private ArrayList<Mans> komiks;

    @SuppressLint({"MissingInflatedId", "SetTextI18n", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        setContentView(R.layout.activity_creator);

        rv_mine = findViewById(R.id.rv_mine);
        btn_back = findViewById(R.id.btn_back);
        btn_new = findViewById(R.id.btn_newcomic);

        getData("mob psycho");

        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(CreatorActivity.this, CreatorUploadActivity.class);
                startActivity(go);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

    }

    private void getData(String value) {
        final String ROOT_URL = "https://fourtour.site/";
        final ProgressDialog loading = ProgressDialog.show(CreatorActivity.this, "Mengambil data", "Mohon tunggu..", false, false);

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

                         adp_hasil = new CardVert(CreatorActivity.this, komiks);
                         rv_mine.setLayoutManager(new LinearLayoutManager(CreatorActivity.this, LinearLayoutManager.VERTICAL, false));
                         rv_mine.setAdapter(adp_hasil);
                     } else {
                         Toast.makeText(CreatorActivity.this, "Gagal mengambil data :(", Toast.LENGTH_SHORT).show();
                     }


                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }

             @Override
             public void onFailure(Call<ManList> call, Throwable t) {
                 loading.dismiss();

                 Toast.makeText(CreatorActivity.this, "Gagal mengambil data :(", Toast.LENGTH_SHORT).show();
             }

         }
        );
    }
}
