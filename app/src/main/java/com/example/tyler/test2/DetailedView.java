package com.example.tyler.test2;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailedView extends AppCompatActivity {

    private static final String TAG = DetailedView.class.getSimpleName();
    String piDocId;
    private TextView detail_get_scope;
    private TextView detail_get_title;
    private TextView detail_get_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        piDocId = getIntent().getExtras().getString("DOC_ID");
        detail_get_id = (TextView) findViewById(R.id.detail_get_id);
        detail_get_title = (TextView) findViewById(R.id.detail_get_title);
        detail_get_scope = (TextView) findViewById(R.id.detail_get_scope);
        Log.d(TAG, "inside Detailed View id: " + piDocId);

        new DetailQueryAsyncTask().execute();
    }

    public class DetailQueryAsyncTask extends AsyncTask<Void, Void, Void> {

        String projleadid;
        String projtitle;
        String scope;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = Uri.parse("http://qa.thebluebook.com/edportal/solrapp.php?q=" + piDocId).buildUpon()
                    .build().toString();
            Log.d(TAG, url);

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                JSONObject json = new JSONObject(responseData);
                JSONObject dataObject = json.getJSONObject("response");
                JSONArray docsArray = dataObject.getJSONArray("docs");

                Log.d(TAG, docsArray.toString() );

                JSONObject doc = docsArray.getJSONObject(0);
                projleadid = doc.getString("projlead_id");
                projtitle = doc.getString("title");
                scope = doc.getString("scope");

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //for now in here i will make 6 textviews 3 will be hard coded to projtitle, bdd and scope
            // the other three will be what is returned from the json
            //Log.d(TAG, "Inside PostExecute projleadid: " + projleadid);

            detail_get_id.setText(projleadid);
            detail_get_title.setText(projtitle);
            detail_get_scope.setText(scope);

        }
    }


}
