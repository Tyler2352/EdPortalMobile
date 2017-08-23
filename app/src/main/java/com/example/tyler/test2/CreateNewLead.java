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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.tyler.test2.R.id.detail_get_id;
import static com.example.tyler.test2.R.id.detail_get_scope;
import static com.example.tyler.test2.R.id.detail_get_title;

public class CreateNewLead extends AppCompatActivity {

    private static final String TAG = DetailedView.class.getSimpleName();
    private TextView newTitle;
    private TextView newScope;
    private TextView newBDD;
    private TextView newUser; // this is hardcoded to me right now will need to change this in the future


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_lead);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newTitle = (TextView) findViewById(R.id.projtitle_submit);
        newScope = (TextView) findViewById(R.id.scope_submit);
        newBDD   = (TextView) findViewById(R.id.date_submit);
        Button mSubNewLead = (Button) findViewById(R.id.submit_new);


        mSubNewLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new submitNewLead().execute();

            }
        });
    }

    public class submitNewLead extends AsyncTask<Void, Void, Void> {

        private String projLeadId;
        private String url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            url = Uri.parse("http://qa.thebluebook.com/edportal/solrnewlead.php?projtitle=" +
                    newTitle.getText().toString() + "&scope=" + newScope.getText().toString() +
                    "&bdd=" + newBDD.getText().toString() + "&user=NYPPCTC").buildUpon()
                    .build().toString();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG, url);

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                JSONObject json = new JSONObject(responseData);
                projLeadId = json.getString("projleadid");

                Log.d(TAG, projLeadId);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(getApplicationContext(), String.format("You have successfully created a project with the Lead ID of %s", projLeadId),Toast.LENGTH_LONG).show();
        }
    }


}


