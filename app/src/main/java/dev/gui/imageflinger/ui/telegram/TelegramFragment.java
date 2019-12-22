package dev.gui.imageflinger.ui.telegram;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.File;

import dev.gui.imageflinger.R;
import dev.gui.imageflinger.ui.GestureListener;

public class TelegramFragment extends Fragment {

    private TelegramViewModel telegramViewModel;
    private View root;
    private TextView textView;
    private ImageView imageView;
    private GestureDetectorCompat mDetector;

    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 42;
    private final String DEBUG_TAG = "TelegramFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_telegram, container, false);
        textView = root.findViewById(R.id.textView_telegram);
        imageView = root.findViewById(R.id.imageView_telegram);

        checkPermission();

        return root;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Allow iteration through the pictures
                    textView.setText("");
                    linkViewModel();
                    addTouchListener();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    textView.setText("permission denied, boo!");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            // DONT SHOW EXPLANATION
            // dont use method provided by ActivityCompat in Fragment!!
//            if (!ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
////                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                // dont use method provided by ActivityCompat in Fragment!!
//                ActivityCompat.requestPermissions(this.getActivity(),
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            linkViewModel();
            addTouchListener();
        }
    }

    private void addTouchListener() {
        mDetector = new GestureDetectorCompat(this.getActivity(), new GestureListener(telegramViewModel));
        root.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    private void linkViewModel() {
        // Permission has already been granted
        telegramViewModel = ViewModelProviders.of(this).get(TelegramViewModel.class);
        telegramViewModel.getImage().observe(this, new Observer<File>() {
            @Override
            public void onChanged(@Nullable File file) {
                Log.e(DEBUG_TAG, "onChanged()");
                if (file != null) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);
                }
            }
        });
    }
}