/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Word>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    public static String earthquakeURL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    private WordAdapter adapter;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    TextView emptyTextView;
    ProgressBar indefiniteLoader;


    @Override
    public Loader<List<Word>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                "min_magnitude",
                "6");
        String orderBy = sharedPrefs.getString(
                "order_by",
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(earthquakeURL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EartquakerLoader(this, uriBuilder.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> words) {
        adapter.clear();
        indefiniteLoader.setVisibility(View.GONE);
        emptyTextView.setText("No Earthquakes to Show");
        if(words!=null && !words.isEmpty()){
            adapter.addAll(words);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Word>> loader) {
        adapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        emptyTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(emptyTextView);
        indefiniteLoader = (ProgressBar) findViewById(R.id.progress_circular);

        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new WordAdapter(this, new ArrayList<Word>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word currEarthquake = adapter.getItem(i);
                Uri earthquakeUri = Uri.parse(currEarthquake.getURL());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }

        else{
            emptyTextView.setText("No Network Detected");
            indefiniteLoader.setVisibility(View.GONE);
        }



    }




}
