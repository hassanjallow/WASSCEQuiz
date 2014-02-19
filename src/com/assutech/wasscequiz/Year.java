package com.assutech.wasscequiz;


import android.app.ListActivity;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.os.Bundle;
//import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Year extends ListActivity {
	
//	SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//	boolean yearFirstSort = getPrefs.getBoolean("yearSort", true);
	String selectedYear, selectedSubject;
	Bundle getSubject;
	Bundle usersSelections;
	
	String subjects[] = { "2013", "2012", "2011", "2010", "2009", "2008",
			"2007", "2006", "2005", "2004", "2003", "2002", "2001", "2001",
			"2000", "1999", "1998" };
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setListAdapter(new ArrayAdapter<String>(Year.this,
				android.R.layout.simple_list_item_1, subjects));
		getSubject = getIntent().getExtras();
		selectedSubject = getSubject.getString("subject");

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater showMenu = getMenuInflater();
		showMenu.inflate(R.menu.year_menu, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
		case R.id.about:
			Intent openMenu = new Intent("com.assutech.wasscequiz.ABOUTMENU");
			startActivity(openMenu);
			break;
		case R.id.preferences:
			Intent openPreferences = new Intent("com.assutech.wasscequiz.PREFS");
			startActivity(openPreferences);
			break;
		case R.id.exit:
			finish();
			break;
		}
		
		return false;
	}
	

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent startQuiz;
		selectedYear = subjects[position];
		if (selectedSubject.equals("englishLanguage.txt")) {
			startQuiz = new Intent(Year.this, EnglishQuiz.class);
		} else {
			startQuiz = new Intent(Year.this, Quiz.class);
		}
		startQuiz.putExtra("year", selectedYear);
		startQuiz.putExtra("subject", selectedSubject);
		startActivity(startQuiz);
	}
	
}
