package com.comikaze;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.comikaze.helpers.LocalStorage;
import com.comikaze.models.UserPreference;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

public class CreatorUploadActivity extends AppCompatActivity {

    private static final int IMAGE_PICK_MODE =  1000;
    private static final int PERMISSION_CODE = 1001;

    private ImageButton btn_back;
    private LinearLayout bottomSheet;
    private ConstraintLayout parentCreatorUpload, viaphone, viacloud;
    private BottomSheetBehavior bottomSheetBehavior;
    private ImageView posterView;
    private EditText inpJudul, inpType;

    private SharedPreferences sp;
    private ArrayList<UserPreference> komikSaya = new ArrayList<>();

    private Uri posterUri;


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
                TextView submit, pickPoster;

                inpJudul = view.findViewById(R.id.judul);
                inpType = view.findViewById(R.id.type);
                submit = view.findViewById(R.id.submit);
                pickPoster = view.findViewById(R.id.pickposter);
                posterView = view.findViewById(R.id.poster);

                pickPoster.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                                requestPermissions(permissions, PERMISSION_CODE);
                            } else {
                                pickImageFromGalerry();
                            }
                        } else {
                            pickImageFromGalerry();
                        }
                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sp = getSharedPreferences("user", MODE_PRIVATE);
                        LocalStorage lokal = new LocalStorage(sp, komikSaya, "komiksaya");
                        komikSaya = lokal.getRiwayatBaca();

                        komikSaya.add(0, new UserPreference(String.valueOf(inpJudul.getText()), String.valueOf(inpType.getText()), String.valueOf(posterUri)));
                        lokal.setRiwayatBaca(komikSaya);

                        Toast.makeText(getApplicationContext(), "Berhasil! Mohon refresh untuk memperbaharui", Toast.LENGTH_SHORT).show();
                        onBackPressed();
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

    private void pickImageFromGalerry() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_MODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if(grantResults.length >0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGalerry();
                }else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_MODE) {
            posterUri = data.getData();
            posterView.setImageURI(posterUri);
        }

    }


}
