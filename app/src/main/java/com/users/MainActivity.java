package com.users;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.users.async.FetchDataTask;
import com.users.model.User;

import java.util.ArrayList;
import java.util.List;

import static com.users.utils.Constants.UPDATE_LIST_ACTION;
import static com.users.utils.Constants.USERS_URL;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<User> usersData;
    private FetchDataTask task;
    private UpdateListReceiver updateListReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateListReceiver = new UpdateListReceiver();

        setUpRecyclerView();
        registerReceiver();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createUserActivity = new Intent(getApplicationContext(), CreateUserActivity.class);
                startActivity(createUserActivity);
            }
        });
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recyclerUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // recyclerview content is fetched from the URL by an Async task.
        // When done, async task will set the recycler adapter

        usersData = new ArrayList<>();

        task = new FetchDataTask(usersData, this, recyclerView);
        task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, USERS_URL);
    }

    private void registerReceiver() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(UPDATE_LIST_ACTION);
            LocalBroadcastManager.getInstance(this).registerReceiver(updateListReceiver, intentFilter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    class UpdateListReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //api does not return avatar image for newly created user, therefore using default image
            User user = new User(intent.getExtras().getString("name"), getBitmap(R.drawable.ic_user));
            usersData.add(user);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private Bitmap getBitmap(int drawableRes) {
        Drawable drawable = getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(updateListReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
