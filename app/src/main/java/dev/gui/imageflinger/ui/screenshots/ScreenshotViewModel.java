package dev.gui.imageflinger.ui.screenshots;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScreenshotViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ScreenshotViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is screenshot fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}