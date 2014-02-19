package com.assutech.wasscequiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizResults extends Activity implements View.OnClickListener {
	
	double percentageScore;
	Intent tryAgain;
	private double points, numberOfQuestions;
	private String subject, year;
	TextView score, remarks;
	Button tryAgainButton, mainMenu, exit;
	Bundle getQuizResults;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_results);
		
		getQuizResults = getIntent().getExtras();
		this.points = getQuizResults.getInt("score");
		this.numberOfQuestions = getQuizResults.getInt("numberOfQuestions");
		this.subject = getQuizResults.getString("subject");
		this.year = getQuizResults.getString("year");
		initialize();
		percentageScore = (points/numberOfQuestions) * 100;
		int percentageScoreRounded = (int)Math.round(percentageScore);
		score.setText(percentageScoreRounded + "%");
		determineRemarks(percentageScore);
	}
	
	private void determineRemarks(double score) {
		//Determines 
		if ((score >= 90) && (score != 100)) {
			this.remarks.setText("Very good!");
		} else if (score == 100) {
			this.remarks.setText("Excellent");
		} else if (score >= 80) {
			this.remarks.setText("Good!");
		} else if (score >= 70) {
			this.remarks.setText("Hey, Not bad!");
		} else if (score >= 60) {
			this.remarks.setText("Wel, try harder next time");
		} else {
			this.remarks.setText("Not good. you need to study!!!");
		}
	}

	private void initialize() {

		score = (TextView) findViewById(R.id.score);
		remarks = (TextView) findViewById(R.id.remarks);
		tryAgainButton = (Button) findViewById(R.id.tryAgain);
		mainMenu = (Button) findViewById(R.id.mainMenu);
		tryAgainButton.setOnClickListener(this);
		mainMenu.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.tryAgain:
			tryAgain = new Intent(QuizResults.this, Quiz.class);
			tryAgain.putExtra("subject", subject);
			tryAgain.putExtra("year", year);
			startActivity(tryAgain);
			break;
		case R.id.mainMenu:
			Intent openSubjectsMenu = new Intent(QuizResults.this, SubjectsMenu.class);
			startActivity(openSubjectsMenu);			
			break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}	
}
