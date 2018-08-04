package com.github.kiolk.sofind.data;

public interface SimpleResultListener {
    void onSuccess();

    void onError(String message);
}
