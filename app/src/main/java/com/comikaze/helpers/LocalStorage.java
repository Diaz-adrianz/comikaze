package com.comikaze.helpers;


import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.comikaze.models.UserPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LocalStorage {

    SharedPreferences sp;
    private String directory;
    private ArrayList<UserPreference> props = new ArrayList<>();

    public LocalStorage(SharedPreferences sp, ArrayList<UserPreference> props, String directory) {
        this.props = props;
        this.sp = sp;
        this.directory = directory;
    }

    private void loadData() {
//        SharedPreferences sharedPreferences =  getSharedPreferences("shared preferences", MODE_PRIVATE);

        Gson gson = new Gson();

        String json = sp.getString(directory, null);

        Type type = new TypeToken<ArrayList<UserPreference>>() {}.getType();

        props = gson.fromJson(json, type);

        if (props == null) {
            props = new ArrayList<>();
        }

    }

    public ArrayList<UserPreference> getRiwayatBaca() {
        loadData();
        return props;
    }

    public void setRiwayatBaca( ArrayList<UserPreference> props) {
        this.props = props;
        saveData();
    }

    private void saveData() {
        SharedPreferences.Editor editor = sp.edit();

        Gson gson = new Gson();

        String json = gson.toJson(props);

        editor.putString(directory, json);

        editor.apply();

    }
}
