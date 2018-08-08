package com.github.kiolk.sofind.data.listeners;

/**
 * Interface for receive success result of method
 */
public interface SimpleResultListener {

    /**
     * Called if execution of method ended by stressful.
     */
    void onSuccess();

    /**
     * Called if execution of method ended by error.
     */
    void onError();
}
