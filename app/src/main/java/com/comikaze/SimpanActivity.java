package com.comikaze;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comikaze.adapters.Card;
import com.comikaze.helpers.LocalStorage;
import com.comikaze.helpers.RecyclerTouchListener;
import com.comikaze.models.UserPreference;

import java.util.ArrayList;

public class SimpanActivity extends AppCompatActivity {

    private RecyclerView rv_simpan;
    private ImageButton btn_back;
    private TextView jumlah;

    private SharedPreferences sp;
    private LocalStorage lokal;
    private Card adp;

    private ArrayList<UserPreference> simpananKomik;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        setContentView(R.layout.activity_simpan);

        sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        lokal = new LocalStorage(sp, simpananKomik, "simpanan");
        simpananKomik = lokal.getRiwayatBaca();

        jumlah = findViewById(R.id.jumlah);
        jumlah.setText("Terdapat " + simpananKomik.size());

        rv_simpan = findViewById(R.id.rv_simpan);

        adp = new Card(this, simpananKomik, "detail");
        rv_simpan.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_simpan.setAdapter(adp);


        rv_simpan.addOnItemTouchListener(new RecyclerTouchListener(this, rv_simpan, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {}

            @Override
            public void onLongClick(View view, int position) {
                final Dialog dialog = new Dialog(SimpanActivity.this);
                dialog.setContentView(R.layout.custom_dialog);
                Button positive = dialog.findViewById(R.id.positive);
                Button negative = dialog.findViewById(R.id.negative);
                TextView head = dialog.findViewById(R.id.head);
                TextView txt = dialog.findViewById(R.id.txt);

                head.setText("Apakah Kamu Yakin?");
                txt.setText("Aksi ini akan menghapus permanen simpanan komik : " + simpananKomik.get(position).getTitle());

                positive.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        simpananKomik.remove(position);
                        adp.notifyDataSetChanged();
                        jumlah.setText("Terdapat " + simpananKomik.size());

                        lokal.setRiwayatBaca(simpananKomik);
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
