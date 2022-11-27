package com.comikaze.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.comikaze.models.UserPreference;

import java.util.ArrayList;
import java.util.List;

public class CardVertMine extends RecyclerView.Adapter<CardVertMine.ViewHoldering> {

    Context context;
    ArrayList<UserPreference> model;

    public CardVertMine(Context context, ArrayList<UserPreference> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public CardVertMine.ViewHoldering onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_cardvert, parent, false);
        ViewHoldering viewHoldering = new ViewHoldering(v);
        return viewHoldering;
    }

    @Override
    public void onBindViewHolder(@NonNull CardVertMine.ViewHoldering h, @SuppressLint("RecyclerView") int i) {
        h.title.setText(model.get(i).getTitle());
        h.type.setText(model.get(i).getSubtitle());
        h.update.setText("Baru saja");

        h.thumb.setImageURI(Uri.parse(model.get(i).getEndpoint()));
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public static class ViewHoldering extends RecyclerView.ViewHolder {
        private final TextView title, type, update;
        private final ImageView thumb;

        public ViewHoldering(@NonNull View itemView) {
            super(itemView);
            thumb = itemView.findViewById(R.id.thumb);
            title = itemView.findViewById(R.id.title);
            type = itemView.findViewById(R.id.type);
            update = itemView.findViewById(R.id.update);
        }
    }
}
