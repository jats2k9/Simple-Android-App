package com.users.async;

import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;

import com.users.adapter.UsersAdapter;
import com.users.model.User;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
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
        //TODO implement logic to retrieve data from API
        return new UsersAdapter(new ArrayList<User>(), contextWeakReference.get());
    }

    @Override
    protected void onPostExecute(UsersAdapter usersAdapter) {
        super.onPostExecute(usersAdapter);
        recyclerViewWeakReference.get().setAdapter(usersAdapter);
    }
}
