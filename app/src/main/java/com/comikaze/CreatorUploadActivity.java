package com.comikaze;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class CreatorUploadActivity extends AppCompatActivity {

    private ImageButton btn_back;
    private LinearLayout bottomSheet;
    private ConstraintLayout parentCreatorUpload, viaphone, viacloud;
    private BottomSheetBehavior bottomSheetBehavior;

    @SuppressLint({"MissingInflatedId", "SetTextI18n", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        setContentView(R.layout.activity_creator_upload);

        btn_back = findViewById(R.id.btn_back);
        viaphone = findViewById(R.id.viaphone);
        viacloud = findViewById(R.id.viacloud);
        parentCreatorUpload = findViewById(R.id.parent);
        bottomSheet = findViewById(R.id.bottomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onStateChanged(View view, int i) {
            }

            @Override
            public void onSlide(View view, float v) {
                // do something when slide happens
                TextView submit = view.findViewById(R.id.submit);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                        Toast.makeText(getApplicationContext(), "Berhasil diunggah", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        viaphone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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


}
