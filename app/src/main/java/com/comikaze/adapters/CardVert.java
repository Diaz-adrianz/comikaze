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

public class CardVert extends RecyclerView.Adapter<CardVert.ViewHoldering> {

    Context context;
    List<Mans> model;

    public CardVert(Context context, List<Mans> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public CardVert.ViewHoldering onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_cardvert, parent, false);
        ViewHoldering viewHoldering = new ViewHoldering(v);
        return viewHoldering;
    }

    @Override
    public void onBindViewHolder(@NonNull CardVert.ViewHoldering h, @SuppressLint("RecyclerView") int i) {
        h.title.setText(model.get(i).getTitle());
        h.type.setText(model.get(i).getType());
        h.update.setText(model.get(i).getUpdate());

        new LoadImageFromInternet(h.thumb).execute(model.get(i).getThumb());

        h.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(context, DetailActivity.class);
                go.putExtra("endpoint", model.get(i).getEndpoint());

                context.startActivity(go);
            }
        });
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
