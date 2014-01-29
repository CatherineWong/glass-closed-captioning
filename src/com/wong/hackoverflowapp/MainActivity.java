package com.wong.hackoverflowapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.glass.app.Card;

public class MainActivity extends Activity {
	Card mainCard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mainCard = new Card(this);
		mainCard.addImage(R.drawable.splash);
		mainCard.setImageLayout(Card.ImageLayout.FULL);
		super.onCreate(savedInstanceState);
		setContentView(mainCard.toView());
		startClosedCaptioning();
	}

	private void startClosedCaptioning() {
		Thread timer = new Thread() {
			// Threads look for methods called "run"
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent ccIntent = new Intent("com.google.android.glass.action.CLOSED_CAPTIONING");
					startActivity(ccIntent);
				}
			}

		};
		// Actually execute the "timer" thread
		timer.start();
	}

}
