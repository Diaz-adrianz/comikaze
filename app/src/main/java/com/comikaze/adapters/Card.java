package com.comikaze.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comikaze.BacaActivity;
import com.comikaze.DetailActivity;
import com.comikaze.R;
import com.comikaze.helpers.LoadImageFromInternet;
import com.comikaze.models.Mans;
import com.comikaze.models.UserPreference;

import java.util.Arrays;
import java.util.List;

public class Card extends RecyclerView.Adapter<Card.ViewHoldering> {

    Context context;
    List<UserPreference> model;
    private String Goto;

    public Card(Context context, List<UserPreference> model, String Goto) {
        this.context = context;
        this.model = model;
        this.Goto = Goto;
    }

    @NonNull
    @Override
    public Card.ViewHoldering onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_cardnoimg, parent, false);
        ViewHoldering viewHoldering = new ViewHoldering(v);
        return viewHoldering;
    }

    @Override
    public void onBindViewHolder(@NonNull Card.ViewHoldering h, @SuppressLint("RecyclerView") int i) {
        h.title.setText(model.get(i).getTitle());
        h.subtitle.setText(model.get(i).getSubtitle());

        if (Goto.equals("chapter")) {
            String[] arrChap = String.valueOf(model.get(i).getSubtitle()).toLowerCase().split(" ");
            int indexChap = Arrays.asList(arrChap).indexOf("chapter");
            String strChap = String.join(" ", Arrays.copyOfRange(arrChap, indexChap, arrChap.length ));

            h.title.setText(strChap);
            h.subtitle.setText(model.get(i).getTitle());
        }

        h.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = null;
                if ( Goto.equals("detail")) {
                    go = new Intent(context, DetailActivity.class);
                    go.putExtra("endpoint", model.get(i).getEndpoint());

                } else if (Goto.equals("chapter")) {
                    go = new Intent(context, BacaActivity.class);
                    go.putExtra("endpoint_chapter", model.get(i).getEndpoint());
                    go.putExtra("title", model.get(i).getTitle());

                    ((Activity)context).finish();
                }

                context.startActivity(go);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public static class ViewHoldering extends RecyclerView.ViewHolder {
        private final TextView title, subtitle;

        public ViewHoldering(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);
        }
    }
}
