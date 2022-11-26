package com.comikaze.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comikaze.DetailActivity;
import com.comikaze.R;
import com.comikaze.helpers.LoadImageFromInternet;
import com.comikaze.models.Mans;

import java.util.List;

public class Halaman extends RecyclerView.Adapter<Halaman.ViewHoldering> {

    Context context;
    List<String> model;

    public Halaman(Context context, List<String> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public Halaman.ViewHoldering onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_halaman, parent, false);
        ViewHoldering viewHoldering = new ViewHoldering(v);
        return viewHoldering;
    }

    @Override
    public void onBindViewHolder(@NonNull Halaman.ViewHoldering h, @SuppressLint("RecyclerView") int i) {
        new LoadImageFromInternet(h.thumb).execute(model.get(i));
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public static class ViewHoldering extends RecyclerView.ViewHolder {
        private final ImageView thumb;

        public ViewHoldering(@NonNull View itemView) {
            super(itemView);
            thumb = itemView.findViewById(R.id.img);
        }
    }
}
