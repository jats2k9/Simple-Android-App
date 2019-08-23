package com.users;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.users.async.PostUserTask;

import static com.users.utils.Constants.USERS_URL;

public class CreateUserActivity extends AppCompatActivity {

    private EditText name;
    private EditText job;
    private Button create;
    private PostUserTask task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuser);

        name = findViewById(R.id.name);
        job = findViewById(R.id.job);
        create = findViewById(R.id.create);

        final Context context = this;

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = name.getText().toString();
                String j = job.getText().toString();

                if (n.equals("") || j.equals("")) {
                    Toast.makeText(context, "Both fields are required", Toast.LENGTH_LONG).show();
                } else {
                    task = new PostUserTask(context);
                    task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, USERS_URL, n, j);
                }
            }
        });

    }
}
