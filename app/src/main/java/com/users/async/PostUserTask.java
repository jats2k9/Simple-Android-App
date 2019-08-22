package com.users.async;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostUserTask extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... strings) {

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", strings[1]);
            jsonObject.put("job", strings[2]);

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
            return sb.toString();

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

