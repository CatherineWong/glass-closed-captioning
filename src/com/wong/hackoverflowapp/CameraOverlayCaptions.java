package com.wong.hackoverflowapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.FrameLayout;


public class CameraOverlayCaptions extends Activity  {
	

    private Camera mCamera;
    private CameraPreview mPreview;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_preview);
		checkCameraHardware(this);
		mCamera = getCameraInstance();
		mPreview = new CameraPreview(this, mCamera);
	    FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
	    preview.addView(mPreview);
	    mCamera.startPreview();
	    
	}

	/** Check if this device has a camera */
	private boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	        return true;
	    } else {
	        // no camera on this device
	        return false;
	    }
	}
	
	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        e.printStackTrace();
	    }
	    return c; // returns null if camera is unavailable
	}

	@Override
	protected void onPause() {
		mCamera.release();
		super.onPause();
	}
	
	

}
