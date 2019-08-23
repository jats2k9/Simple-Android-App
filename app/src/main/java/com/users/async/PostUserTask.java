package com.users.async;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.users.CreateUserActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.users.utils.Constants.UPDATE_LIST_ACTION;

public class PostUserTask extends AsyncTask<String, String, String> {
    private WeakReference<Context> contextWeakReference;

    public PostUserTask(Context context) {
        this.contextWeakReference = new WeakReference<>(context);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            String name = strings[1];
            String job = strings[2];

            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            jsonObject.put("job", job);

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            wr.flush();
            wr.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

            //parse response
            JSONObject jsonResponse = new JSONObject(sb.toString());

            return jsonResponse.getString("name");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(String name) {
        if (name != null) {
            // send broadcast to refresh list of users
            Intent intentUpdateUsers = new Intent();
            intentUpdateUsers.putExtra("name", name);
            intentUpdateUsers.setAction(UPDATE_LIST_ACTION);
            LocalBroadcastManager.getInstance(contextWeakReference.get()).sendBroadcast(intentUpdateUsers);

            ((CreateUserActivity) contextWeakReference.get()).finish();
        } else {
            //if response fails to send valid data
            Toast.makeText(contextWeakReference.get(), "Unable to create user", Toast.LENGTH_LONG).show();
            ((CreateUserActivity) contextWeakReference.get()).finish();
        }

    }
}

