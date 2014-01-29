package com.wong.hackoverflowapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MotionEvent;

import com.google.android.glass.app.Card;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

public class ClosedCaptioning extends Activity {

	private GestureDetector mGestureDetector;
	private Card card;
	public static final int REQUEST_CODE = 111;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		createCard();
		super.onCreate(savedInstanceState);
		setContentView(card.toView());
		mGestureDetector = createGestureDetector (this);

	}
	private void createCard(){
		card = new Card(this);
		card.setText("Tap to begin captioning...");
	}
	

	private GestureDetector createGestureDetector(Context context) {
	    GestureDetector gestureDetector = new GestureDetector(context);
	        //Create a base listener for generic gestures
	        gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
	            @Override
	            public boolean onGesture(Gesture gesture) {
	                if (gesture == Gesture.TAP) {
	                	//Intent startCaptioning = new Intent("com.google.android.glass.action.CAMERA_OVERLAY_CAPTIONS");
	                	//startActivity(startCaptioning);
	                	Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

	        			//Determine which language was spoken - use default RecognizerIntent key and values for this
	        			//This extra is required when using the recognize speech action
	        			i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
	        			//Add a prompt when speaking, if you wish
	        			i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak away!");
	        			//Start the activity for a result - include the intent and a request code key to receive the result code
	        			startActivityForResult(i, REQUEST_CODE);
	        			card.setText("You tapped!");
	                    setContentView(card.toView());																																					
	                    return true; }
//	                } else if (gesture == Gesture.TWO_TAP) {
//	                    // do something on two finger tap
//	                    return true;
//	                } else if (gesture == Gesture.SWIPE_RIGHT) {
//	                    // do something on right (forward) swipe
//	                    return true;
//	                } else if (gesture == Gesture.SWIPE_LEFT) {
//	                    // do something on left (backwards) swipe
//	                    return true;
//	                }
	                return false;
	            }
	        });
//	        gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
//	            @Override
//	            public void onFingerCountChanged(int previousCount, int currentCount) {
//	              // do something on finger count changes
//	            }
//	        });
//	        gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
//	            @Override
//	            public boolean onScroll(float displacement, float delta, float velocity) {
//	                // do something on scrolling
//	            }
//	        });
	        return gestureDetector;
	    }

	    /*
	     * Send generic motion events to the gesture detector
	     */
	    @Override
	    public boolean onGenericMotionEvent(MotionEvent event) {
	        if (mGestureDetector != null) {
	            return mGestureDetector.onMotionEvent(event);
	        }
	        return false;
	    }
	    
	    @Override
		protected void onActivityResult(int request, int resultCode, Intent data) {
			//Check which activity started the result and that the result_oks
			if(request == REQUEST_CODE && resultCode == RESULT_OK){
				//Collect results (possible answers, from best to worse) in an array list from the extras
				ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				//set the list view to this array list, using an array adapter (with context, android layout, and the data in the list
				card.setText(result.get(0));
				setContentView(card.toView());
			}
			super.onActivityResult(REQUEST_CODE, resultCode, data);
		}
	}
