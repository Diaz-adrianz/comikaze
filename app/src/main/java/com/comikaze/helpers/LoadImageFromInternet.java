package com.comikaze.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;


public class LoadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    public LoadImageFromInternet(ImageView imageView) {
        this.imageView=imageView;
    }
    protected Bitmap doInBackground(String... urls) {
        String imageURL=urls[0];
        Bitmap bimage=null;
        try {
            InputStream in=new java.net.URL(imageURL).openStream();
            bimage= BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error Message", e.getMessage());
            e.printStackTrace();
//            Toast.makeText(imageView.getContext(), "Gagal memu :(", Toast.LENGTH_SHORT).show();

        }
        return bimage;
    }
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
