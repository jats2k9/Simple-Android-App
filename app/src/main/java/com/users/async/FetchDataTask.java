package com.users.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;

import com.users.adapter.UsersAdapter;
import com.users.model.User;
import com.users.utils.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

public class FetchDataTask extends AsyncTask<String, Integer, UsersAdapter> {

    private WeakReference<RecyclerView> recyclerViewWeakReference;
    private WeakReference<Context> contextWeakReference;
    private List<User> usersData;


    public FetchDataTask(List<User> usersData, Context context, RecyclerView recyclerView) {
        this.recyclerViewWeakReference = new WeakReference<>(recyclerView);
        this.contextWeakReference = new WeakReference<>(context);
        this.usersData = usersData;
    }

    @Override
    protected UsersAdapter doInBackground(String... strings) {

        String response = NetworkUtil.getJSON(strings[0]);

        JSONObject result;
        try {
            result = new JSONObject(response);

            JSONArray jsonArray = result.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject userJson = jsonArray.getJSONObject(i);

                Bitmap avatar = NetworkUtil.getBitmapFromURL(userJson.getString("avatar"));

                User user = new User(userJson.getString("first_name") +
                        " " +
                        userJson.getString("last_name"), avatar);

                usersData.add(user);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new UsersAdapter(usersData, contextWeakReference.get());
    }

    @Override
    protected void onPostExecute(UsersAdapter usersAdapter) {
        super.onPostExecute(usersAdapter);
        recyclerViewWeakReference.get().setAdapter(usersAdapter);
    }
}
