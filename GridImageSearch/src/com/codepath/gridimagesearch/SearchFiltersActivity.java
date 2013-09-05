package com.codepath.gridimagesearch;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchFiltersActivity extends Activity {
	
	Spinner spnSize, spnColor, spnType;
	EditText etSite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_filters);
		setupViews();
		setupSpinner(this.spnSize, R.array.size_array);
		setupSpinner(this.spnColor, R.array.color_array);
		setupSpinner(this.spnType, R.array.type_array);
	}
	
	public void setupViews() {
		spnSize = (Spinner) findViewById(R.id.spnSize);
		spnColor = (Spinner) findViewById(R.id.spnColor);
		spnType = (Spinner) findViewById(R.id.spnType);
		etSite = (EditText) findViewById(R.id.etSite);
	}
	
	public void setupSpinner(Spinner spinner, int arrayId) {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				arrayId, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
	
	public void onSave(View v) {
		String[] args = new String[4];
		String size = spnSize.getSelectedItem().toString();
		args[0] = size.equals("any") ? null : size;
		String color = spnColor.getSelectedItem().toString();
		args[1] = color.equals("any") ? null : color;
		String type = spnType.getSelectedItem().toString();
		args[2] = type.equals("any") ? null : type;
		args[3] = etSite.getText().toString();

		SearchActivity.setSearchFilters(args);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_filters, menu);
		return true;
	}

}
