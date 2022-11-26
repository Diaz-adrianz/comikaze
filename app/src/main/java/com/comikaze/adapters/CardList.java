package com.comikaze.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.comikaze.R;
import com.comikaze.models.Details_chapters;

import java.util.ArrayList;

public class CardList extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Details_chapters> model;

    public CardList(Context context, ArrayList<Details_chapters> model)
    {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.model = model;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public  Object getItem(int index) {
        return model.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TextView title;
    @SuppressLint({"ViewHolder", "SetTextI18n", "MissingInflatedId"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.item_cardlist, viewGroup, false);

        title = v.findViewById(R.id.title);

        title.setText(model.get(i).getChapterTitle());
        return v;
    }


}

