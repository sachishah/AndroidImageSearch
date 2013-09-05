package com.codepath.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	Button btnFilters;
	Button btnMore;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultsArrayAdapter imageAdapter;

	static String size;
	static String color;
	static String type;
	static String site = "";
	
	static int start = 0;
	static String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        imageAdapter = new ImageResultsArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("url", imageResult.getFullUrl());
				startActivity(i);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    public void setupViews() {
    	etQuery = (EditText) findViewById(R.id.etQuery);
    	gvResults = (GridView) findViewById(R.id.gvResults);
    	btnSearch = (Button) findViewById(R.id.btnSearch);
    	btnFilters = (Button) findViewById(R.id.btnFilters);
    	btnMore = (Button) findViewById(R.id.btnMore);
    }

    public void onImageSearch(View v) {
    	query = etQuery.getText().toString();
    	if (query.matches("")) {
    		Toast.makeText(this, "Please enter query", Toast.LENGTH_SHORT).show();
    		return;
    	}
	    Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT).show();
	   	loadResults();
    }
    
    private void loadResults() {
    	AsyncHttpClient client = new AsyncHttpClient();
	    client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" +
	    			"imgsz=" + size + "&imgcolor=" + color + "&imgtype=" + type + 
	    			"&as_sitesearch=" + site + "&start=" + start + "&v=1.0&q=" + Uri.encode(query), 
	    			new JsonHttpResponseHandler() {
	   		@Override
	   		public void onSuccess(JSONObject response) {
	   			JSONArray imageJsonResults = null;
	   			try {
    				imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
	    			imageResults.clear();
	    			imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
	    			Log.d("DEBUG", imageResults.toString());
	    		} catch (JSONException e) {
	   				e.printStackTrace();
	   			}
	   		}
	   	});
    }

    public void onSetFilters(View v) {
    	Intent i = new Intent(getApplicationContext(), SearchFiltersActivity.class);
		startActivity(i);
    }

    public static void setSearchFilters(String[] args) {
    	size = args[0];
    	color = args[1];
    	type = args[2];
    	site = args[3];
    }
    
    public void loadMore(View v) {
    	start += 8;
    	loadResults();
    }
}
