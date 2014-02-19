package com.assutech.wasscequiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Welcome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_layout);
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent openSubjectsMenu = new Intent("com.assutech.wasscequiz.SUBJECTSMENU");
					startActivity(openSubjectsMenu);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
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