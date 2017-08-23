package com.example.tyler.test2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SolrActivity extends AppCompatActivity {

    private static final String TAG = SolrActivity.class.getSimpleName();

    private Menu mMenu;
    Button btnSubmit;
    private AutoCompleteTextView mQuery;
    private RecyclerView recyclerView;
    private DocAdapter mAdapter;
    //private final Context mSContext;
    List<Doc> docList = new ArrayList<>();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solr);

        btnSubmit = (Button) findViewById(R.id.submit_search);
        recyclerView = (RecyclerView) findViewById(R.id.show_results);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                subQuery();

                new QueryAsyncTask().execute();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_solr_activity, mMenu);
        hideOption(mMenu, R.id.action_add);
        return true;
    }

    //methods
    private void hideOption(Menu menu, int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(Menu menu, int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                //Log.d(TAG, "Add button was selected");
                Intent intent = new Intent(SolrActivity.this, CreateNewLead.class);
                SolrActivity.this.startActivity(intent);
                return true;
                default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void subQuery() {


        mQuery = (AutoCompleteTextView) findViewById(R.id.query_string);

        String url = Uri.parse("http://qa.thebluebook.com/edportal/solrapp.php?q=" + mQuery.getText().toString()).buildUpon()
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

            for (int i = 0; i < docsArray.length(); i++) {
                JSONObject doc = docsArray.getJSONObject(i);
                String id = doc.getString("id");
                String projlead = doc.getString("projlead_id");
                String projtitle = doc.getString("title");

                docList.add(new Doc(id, projlead, projtitle));
            }

            Log.d(TAG, docList.toString());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Solr Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        AppIndex.AppIndexApi.start(client2, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client2, getIndexApiAction());
        client2.disconnect();
    }


    public class QueryAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            subQuery();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            showOption(mMenu, R.id.action_add);
            Log.d(TAG, "Inside PostExecute");
            mAdapter = new DocAdapter(SolrActivity.this, docList);
            recyclerView.setAdapter(mAdapter);
        }
    }
}
