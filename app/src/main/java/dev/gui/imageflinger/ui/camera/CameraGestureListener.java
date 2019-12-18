package dev.gui.imageflinger.ui.camera;

import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

import dev.gui.imageflinger.ui.Gestures;

public class CameraGestureListener extends SimpleOnGestureListener {

    private final String DEBUG_TAG = "CameraGestureListener";
    private CameraViewModel cameraViewModel;

    public CameraGestureListener(CameraViewModel cameraViewModel) {
        this.cameraViewModel = cameraViewModel;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.e(DEBUG_TAG, "onDown" + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.e(DEBUG_TAG, "onFling" + e1.toString() + e2.toString());

        final int SWIPE_MIN_DISTANCE = 120;
        final int SWIPE_MAX_OFF_PATH = 250;
        final int SWIPE_THRESHOLD_VELOCITY = 200;
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.e(DEBUG_TAG, "Right to Left");
                cameraViewModel.next(Gestures.RIGHT_TO_LEFT.ordinal());
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.e(DEBUG_TAG, "Left to Right");
                cameraViewModel.next(Gestures.LEFT_TO_RIGHT.ordinal());
            }
        } catch (Exception e) {
            // nothing
        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
