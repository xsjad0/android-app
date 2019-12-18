package dev.gui.imageflinger.ui.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;

public class CameraViewModel extends ViewModel {

    private Integer index = 0;
    private String path;
    private File[] images;
    private MutableLiveData<File> mImage;

    public CameraViewModel() {
        mImage = new MutableLiveData<>();
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera";

        File dir = new File(path);
        images = dir.listFiles();
        if (images[index].isFile()) {
            mImage.setValue(images[index]);
        }
    }

    public LiveData<File> getImage() {
        return mImage;
    }

    public void next(int direction) {
        if (direction == 0) {
            if (index < images.length - 1) {
                ++index;
            }
            else {
                index = 0;
            }
        }
        else {
            if (index > 0) {
                --index;
            }
            else {
                index = images.length -1;
            }
        }

        mImage.setValue(images[index]);
    }

}