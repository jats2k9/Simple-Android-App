package com.users;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.users.async.FetchDataTask;
import com.users.model.User;

import static com.users.utils.Constants.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<User> usersDara;
    private FetchDataTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpRecyclerView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recyclerUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // recyclerview content is fetched from the URL by an Async task.
        // When done, async task will set the recycler adapter

        usersDara = new ArrayList<>();

        task = new FetchDataTask(usersDara, this, recyclerView);
        task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, USERS_URL);
    }

}
