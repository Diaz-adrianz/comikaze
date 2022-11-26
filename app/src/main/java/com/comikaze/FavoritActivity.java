package com.comikaze;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comikaze.adapters.Card;
import com.comikaze.adapters.Halaman;
import com.comikaze.helpers.LocalStorage;
import com.comikaze.helpers.RecyclerTouchListener;
import com.comikaze.models.Chapters;
import com.comikaze.models.UserPreference;
import com.comikaze.rest.Rest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoritActivity extends AppCompatActivity {

    private RecyclerView rv_fav;
    private ImageButton btn_back;
    private TextView jumlah;

    private SharedPreferences sp;
    private LocalStorage lokal;
    private Card adp;

    private ArrayList<UserPreference> favoritKomik;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        setContentView(R.layout.activity_favorit);

        sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        lokal = new LocalStorage(sp, favoritKomik, "favorit");
        favoritKomik = lokal.getRiwayatBaca();

        jumlah = findViewById(R.id.jumlah);
        jumlah.setText("Terdapat " + favoritKomik.size());

        rv_fav = findViewById(R.id.rv_fav);

        adp = new Card(this, favoritKomik, "detail");
        rv_fav.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_fav.setAdapter(adp);

        rv_fav.addOnItemTouchListener(new RecyclerTouchListener(this, rv_fav, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {}

            @Override
            public void onLongClick(View view, int position) {
                final Dialog dialog = new Dialog(FavoritActivity.this);
                dialog.setContentView(R.layout.custom_dialog);
                Button positive = dialog.findViewById(R.id.positive);
                Button negative = dialog.findViewById(R.id.negative);
                TextView head = dialog.findViewById(R.id.head);
                TextView txt = dialog.findViewById(R.id.txt);

                head.setText("Apakah Kamu Yakin?");
                txt.setText("Aksi ini akan menghapus permanen favorit komik : " + favoritKomik.get(position).getTitle());

                positive.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        favoritKomik.remove(position);
                        adp.notifyDataSetChanged();
                        jumlah.setText("Terdapat " + favoritKomik.size());

                        lokal.setRiwayatBaca(favoritKomik);
                        dialog.dismiss();
                    }
                });

                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        }));

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

    }

}
