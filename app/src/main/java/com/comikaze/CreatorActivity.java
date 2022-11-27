package com.comikaze;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comikaze.adapters.CardVert;
import com.comikaze.adapters.CardVertMine;
import com.comikaze.helpers.LoadImageFromInternet;
import com.comikaze.helpers.LocalStorage;
import com.comikaze.helpers.RecyclerTouchListener;
import com.comikaze.models.ManList;
import com.comikaze.models.Mans;
import com.comikaze.models.UserPreference;
import com.comikaze.rest.Rest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreatorActivity extends AppCompatActivity {

    private ImageButton btn_back, btn_refresh;
    private Button btn_new;
    private RecyclerView rv_mine;
    private CardVertMine adp_hasil;

    private SharedPreferences sp;
    private ArrayList<UserPreference> komikSaya;

    private LinearLayout parentXML;
    private TextView jumlahView;

    @SuppressLint({"MissingInflatedId", "SetTextI18n", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        setContentView(R.layout.activity_creator);

        parentXML = findViewById(R.id.parentXML);

        jumlahView = findViewById(R.id.jumlah);
        rv_mine = findViewById(R.id.rv_mine);
        btn_refresh = findViewById(R.id.btn_refresh);
        btn_back = findViewById(R.id.btn_back);
        btn_new = findViewById(R.id.btn_newcomic);

        sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        LocalStorage lokal = new LocalStorage(sp, komikSaya, "komiksaya");
        komikSaya = lokal.getRiwayatBaca();

        jumlahView.setText("Terdapat " + komikSaya.size());

        adp_hasil = new CardVertMine(CreatorActivity.this, komikSaya);
        rv_mine.setLayoutManager(new LinearLayoutManager(CreatorActivity.this, LinearLayoutManager.VERTICAL, false));
        rv_mine.setAdapter(adp_hasil);

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

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                Intent t= new Intent(CreatorActivity.this, CreatorActivity.class);
                startActivity(t);
                finish();
            }
        });

        rv_mine.addOnItemTouchListener(new RecyclerTouchListener(this, rv_mine, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {}

            @Override
            public void onLongClick(View view, int position) {
                final Dialog dialog = new Dialog(CreatorActivity.this);
                dialog.setContentView(R.layout.custom_dialog);
                Button positive = dialog.findViewById(R.id.positive);
                Button negative = dialog.findViewById(R.id.negative);
                TextView head = dialog.findViewById(R.id.head);
                TextView txt = dialog.findViewById(R.id.txt);

                head.setText("Apakah Kamu Yakin?");
                txt.setText("Aksi ini akan menghapus permanen komik : " + komikSaya.get(position).getTitle());

                positive.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        komikSaya.remove(position);
                        adp_hasil.notifyDataSetChanged();
                        jumlahView.setText("Terdapat " + komikSaya.size());

                        lokal.setRiwayatBaca(komikSaya);
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
    }


}
