package com.users;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateUserActivity extends AppCompatActivity {

    private EditText name;
    private EditText job;
    private Button create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuser);

        name = findViewById(R.id.name);
        job = findViewById(R.id.job);
        create = findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = name.getText().toString();
                String j = job.getText().toString();

                if (n.equals("") || j.equals("")) {
                    Toast.makeText(getApplicationContext(), "Both fields are required", Toast.LENGTH_LONG).show();
                } else {

                }
            }
        });

    }
}
