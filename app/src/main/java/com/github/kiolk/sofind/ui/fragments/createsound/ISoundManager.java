package com.github.kiolk.sofind.ui.fragments.createsound;

import com.github.kiolk.sofind.data.SimpleResultListener;
import com.github.kiolk.sofind.data.models.SofindModel;

public interface ISoundManager {

    void updateNewSound(SofindModel sofind, SimpleResultListener listener);
}
