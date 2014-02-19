package com.assutech.wasscequiz;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SubjectsMenu extends Activity implements View.OnClickListener {
	
	Button mathematics, englishLanguage, government, history, biology, economics, agriculture;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subjects_menu);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		mathematics = (Button) findViewById(R.id.mathematics);
		englishLanguage = (Button) findViewById(R.id.englishLanguage);
		government = (Button) findViewById(R.id.government);
		history = (Button) findViewById(R.id.history);
		biology = (Button) findViewById(R.id.biology);
		economics = (Button) findViewById(R.id.economics);
		agriculture = (Button) findViewById(R.id.agriculture);
		mathematics.setOnClickListener(this);
		englishLanguage.setOnClickListener(this);
		government.setOnClickListener(this);
		history.setOnClickListener(this);
		biology.setOnClickListener(this);
		economics.setOnClickListener(this);
		agriculture.setOnClickListener(this);	
	}

	@Override
	public void onClick(View v) {
		/*Determines which button was click
		 * passes the selected subject to 
		 * subjectSelected()
		 */
		switch(v.getId()) {
		case R.id.englishLanguage:
			subjectSelected("englishLanguage.txt");
			break;
		case R.id.mathematics:
			subjectSelected("mathematics.txt");
			break;
		case R.id.government:
			subjectSelected("government.txt");
			break;
		case R.id.history:
			subjectSelected("history.txt");
		case R.id.biology:
			subjectSelected("biology.txt");
			break;
		case R.id.economics:
			subjectSelected("economics.txt");
			break;
		case R.id.agriculture:
			subjectSelected("agriculture.txt");
			break;
		}
	}

	private void subjectSelected(String subjectSelected) {
		/*Creates a Bundle and put the select subject into it
		 * Create an Intent and add the bundle to it
		 * then start Year activity
		 */
		Bundle subject = new Bundle();
		subject.putString("subject", subjectSelected); //Add selected
		Intent openYearMenu = new Intent(SubjectsMenu.this, Year.class);
		openYearMenu.putExtras(subject);
		startActivity(openYearMenu);
	}
}

/*
 * Assutech 2013-2014 | All rights reserved
 * Graphics by Sulayman Sanyang
 * Website by Pa Modou Samateh
 * All coding by Ibn Yusuf
 * All credits go to the Assutech team
 * Email:assutech@gmail.com
 * web: www.assutech.com
 */