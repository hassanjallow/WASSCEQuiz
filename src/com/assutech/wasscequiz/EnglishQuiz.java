package com.assutech.wasscequiz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class EnglishQuiz extends Activity implements View.OnClickListener {

	private TextView hint;
	private TextView question;
	private Button option1, option2, option3, option4, next, previous;
	private String subject, year; // Year and subject selected
	private Boolean questionAnswered = false;
	private InputStream in = null;
	private BufferedReader reader;
	private int questionNumber = 0, numberOfQuestions, rightAnswer;
	private int points = 0;// Score in the quiz
	Intent openQuizResults;
	int numberOfQuestionsAnswered;
	private String getThread[];// Current question, options and correct option
	/*
	 * quizProgress[] saves which questions are answered correctly and which
	 * questions are answered incorrectly
	 */
	private Boolean quizProgress[], previousButtonWasPressed = false;
	ArrayList<String> fullQuestionThread = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.english_quiz_main);
		initialize();
		setUpQuiz();
	}

	private void initialize() {

		hint = (TextView) findViewById(R.id.hint);
		question = (TextView) findViewById(R.id.question);
		option1 = (Button) findViewById(R.id.option1);
		option2 = (Button) findViewById(R.id.option2);
		option3 = (Button) findViewById(R.id.option3);
		option4 = (Button) findViewById(R.id.option4);
		next = (Button) findViewById(R.id.next);
		previous = (Button) findViewById(R.id.previous);
		option1.setOnClickListener(this);
		option2.setOnClickListener(this);
		option3.setOnClickListener(this);
		option4.setOnClickListener(this);
		next.setOnClickListener(this);
		previous.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		/*
		 * checks which button the user clicks and performs a corresponding
		 * action if next button is clicked and the current question is already
		 * answered it fills the widgets with the contents of the next question
		 * else nothing happens
		 */
		switch (v.getId()) {
		case R.id.next:
			if ((questionAnswered) && (questionNumber < numberOfQuestions)) {
				questionNumber++;
				if (questionNumber >= numberOfQuestionsAnswered) {
					previousButtonWasPressed = false;
				}
				restoreButtonState();// Removes color applied to buttons
				fillWidgets();// Fills the question and options
				if (questionNumber < numberOfQuestionsAnswered) {// if question
																	// is
																	// already
																	// answered
					questionAnswered = true;
					if (quizProgress[questionNumber] == true) {// if question
							// was answered
																// correctly
						hint.setText(getThread[1]);
						clickedOption(rightAnswer);// make the button green
					} else {
						answerIsWrong();// make the button red
					}
				} else {
					questionAnswered = false;
				}
			}

			break;
		case R.id.previous:
			if (questionNumber > 0) {
				this.previousButtonWasPressed = true;
				this.questionNumber--;// Back to the previous question
				restoreButtonState();
				fillWidgets();
				if (quizProgress[questionNumber] == true) {
					clickedOption(this.rightAnswer);
				} else {
					answerIsWrong();
				}
			}
			break;

		case R.id.option1:
			if (questionAnswered == false) {
				clickedOption(1);
			}
			break;
		case R.id.option2:
			if (questionAnswered == false) {
				clickedOption(2);
			}
			break;
		case R.id.option3:
			if (questionAnswered == false) {
				clickedOption(3);
			}
			break;
		case R.id.option4:
			if (questionAnswered == false) {
				clickedOption(4);
			}
			break;
		}
	}

	private void clickedOption(int optionClicked) {
		/*
		 * Gets the clickOption @param optionClicked and Checks if == the
		 * correct answer
		 */
		if (optionClicked == rightAnswer) {

			if (previousButtonWasPressed == false) {
				numberOfQuestionsAnswered++;
				points++;
			}

			switch (optionClicked) {
			// sets the the color of the button to green
			// Since the answer was correct
			case 1:
				option1.setBackgroundColor(Color.rgb(118, 137, 76));
				break;
			case 2:
				option2.setBackgroundColor(Color.rgb(118, 137, 76));
				break;
			case 3:
				option3.setBackgroundColor(Color.rgb(118, 137, 76));
				break;
			case 4:
				option4.setBackgroundColor(Color.rgb(118, 137, 76));
				break;
			}

			questionAnsweredCorrectly(true);
		} else {
			answerIsWrong();
		}
		questionAnswered = true;

		if ((questionNumber + 1) == numberOfQuestions) {// questionNumber index
														// starts at 0

			Bundle quizResults = new Bundle();
			openQuizResults = new Intent(EnglishQuiz.this, QuizResults.class);

			quizResults.putInt("score", this.points);
			quizResults.putInt("numberOfQuestions", this.numberOfQuestions);
			quizResults.putString("year", this.year);
			quizResults.putString("subject", this.subject);
			openQuizResults.putExtras(quizResults);
			startActivity(openQuizResults);
		}

	}

	private void answerIsWrong() {
		/*
		 * Called when the option clicked != correct answer
		 */
		switch (this.rightAnswer) {
		// sets color of the button to red
		// since the question was not answered correctly
		case 1:
			option1.setBackgroundColor(Color.rgb(131, 8, 11));
			break;
		case 2:
			option2.setBackgroundColor(Color.rgb(131, 8, 11));
			break;
		case 3:
			option3.setBackgroundColor(Color.rgb(131, 8, 11));
			break;
		case 4:
			option4.setBackgroundColor(Color.rgb(131, 8, 11));
			break;
		}
		questionAnsweredCorrectly(false);
	}

	private void setUpQuiz() {
		/*
		 * Connects to the subject file and picks out the question with the
		 * right year based on the selections from the previous pages
		 */
		Intent usersSelections = getIntent();
		this.subject = usersSelections.getExtras().getString("subject");
		this.year = usersSelections.getExtras().getString("year");
		String line = null;

		try {
			in = this.getAssets().open(subject); // Reads from question file
		} catch (IOException e) {
			e.printStackTrace();
		}
		reader = new BufferedReader(new InputStreamReader(in));
		String filter;
		try {
			do {
				line = reader.readLine();
				filter = line + ""; // Current line to filter for
				if (filter.startsWith(year)) { // To pick out the selected
												// subject from the text file
					fullQuestionThread.add(numberOfQuestions, line);
					numberOfQuestions++;
				}
			} while (line != null);
		} catch (IOException e) {																		
			e.printStackTrace();
		}
		fillWidgets();// Set the first question

		// To store whether a particular question number
		// is answered correctly or incorrectly.
		quizProgress = new Boolean[numberOfQuestions];
	}

	private void restoreButtonState() {
		/*
		 * Remove color and restore the buttons to default state
		 */
		option1.setBackgroundResource(android.R.drawable.btn_default);
		option2.setBackgroundResource(android.R.drawable.btn_default);
		option3.setBackgroundResource(android.R.drawable.btn_default);
		option4.setBackgroundResource(android.R.drawable.btn_default);
	}

	public void questionAnsweredCorrectly(Boolean wasAnsweredCorrectly) {
		/*
		 * Saves whether a particular question was answered correctly To
		 * remember how the color the button when the back button is clicked
		 */
		quizProgress[questionNumber] = wasAnsweredCorrectly;
	}

	private void fillWidgets() {
		/*
		 * gets full question as one string where the question, four options and
		 * correct answers are delimited by '#'. Then splits them and assigns
		 * each to an index of getThread[].Also takes the correct answer & converts
		 * it to int
		 */
		getThread = (fullQuestionThread.get(questionNumber)).split("#");
		
	
		
		hint.setText(getThread[1]);
		question.setText(getThread[2]);
		option1.setText(getThread[3]);
		option2.setText(getThread[4]);
		option3.setText(getThread[5]);
		option4.setText(getThread[6]);
		this.rightAnswer = Integer.parseInt((getThread[7] + ""));
		

	}

	@Override
	protected void onPause() {

		super.onPause();
		finish();
	}
}