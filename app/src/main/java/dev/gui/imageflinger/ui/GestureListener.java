package dev.gui.imageflinger.ui;

import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

import androidx.lifecycle.ViewModel;

import dev.gui.imageflinger.ui.camera.CameraViewModel;
import dev.gui.imageflinger.ui.telegram.TelegramViewModel;
import dev.gui.imageflinger.ui.screenshots.ScreenshotViewModel;

public class GestureListener extends SimpleOnGestureListener {

    private final int SWIPE_MIN_DISTANCE = 120;
    private final int SWIPE_MAX_OFF_PATH = 250;
    private final int SWIPE_THRESHOLD_VELOCITY = 200;
    private final String DEBUG_TAG = "GestureListener";
    private ViewModel viewModel;

    public GestureListener(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.e(DEBUG_TAG, "onDown" + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.e(DEBUG_TAG, "onFling" + e1.toString() + e2.toString());
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.e(DEBUG_TAG, "Right to Left");
                action(Gestures.RIGHT_TO_LEFT.ordinal());
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.e(DEBUG_TAG, "Left to Right");
                action(Gestures.LEFT_TO_RIGHT.ordinal());
            }
        } catch (Exception e) {
            // nothing
        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    private void action(int direction) {
        if (viewModel instanceof CameraViewModel) {
            CameraViewModel vm = (CameraViewModel) viewModel;
            vm.next(direction);
        } else if (viewModel instanceof TelegramViewModel) {
            TelegramViewModel vm = (TelegramViewModel) viewModel;
            vm.next(direction);
        } else {
            ScreenshotViewModel vm = (ScreenshotViewModel) viewModel;
            vm.next(direction);
        }
    }
}
