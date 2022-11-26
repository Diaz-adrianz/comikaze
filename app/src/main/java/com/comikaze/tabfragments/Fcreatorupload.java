//package com.comikaze.tabfragments;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.comikaze.R;
//
////import droidninja.filepicker.FilePickerBuilder;
////import droidninja.filepicker.FilePickerConst;
////import pub.devrel.easypermissions.EasyPermissions;
//
//public class Fcreatorupload extends AppCompatActivity {
//
//    ImageView takeGalery, poster;
//    private static final int IMAGE_PICK_MODE = 1000;
//    private static final int PERMISSION_CODE = 1001;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        takeGalery = findViewById(R.id.takeGalery);
//        poster = findViewById(R.id.poster);
//
//        takeGalery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                            == PackageManager.PERMISSION_DENIED) {
//                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
//                        requestPermissions(permissions, PERMISSION_CODE);
//                    }else {
//                        pickImageFromGalerry();
//
//                    }
//
//                }else {
//                    pickImageFromGalerry();
//                }
//            }
//        });
//    }
//
//    private void pickImageFromGalerry() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, IMAGE_PICK_MODE);
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PERMISSION_CODE: {
//                if(grantResults.length >0 && grantResults[0] ==
//                        PackageManager.PERMISSION_GRANTED) {
//                    pickImageFromGalerry();
//                }else {
//                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_MODE) {
//            poster.setImageURI(data.getData());
//        }
//
//    }
//
//    //
////        takeGalery.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                String[] strings = {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE};
////                if (EasyPermissions.hasPermissions(this, strings)) {
////                    imagePicker();
////                } else {
////                    EasyPermissions.requestPermissions(
////                            this,
////                            "App need access to your camera and storage",
////                            100,
////                            strings
////                    );
////                }
////            }
////        });
////
////    }
////
////    private void imagePicker() {
////        FilePickerBuilder.getInstance()
////                .setActivityTitle("Select Images")
////                .setSpan(FilePickerConst.SPAN_TYPE.FOLDER_SPAN,3)
////                .setSpan(FilePickerConst.SPAN_TYPE.DETAIL_SPAN,4)
////                .setMaxCount(4)
////                .setSelectedFiles()
////
////    }
//}