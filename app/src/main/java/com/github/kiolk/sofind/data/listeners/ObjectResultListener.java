package com.github.kiolk.sofind.data.listeners;

/**
 * Interface of simple listener. Using interface for receiving object, from method where this
 * listener call.
 */
public interface ObjectResultListener {

    /**
     * Method used for receiving object from method where it call, to method where it implement
     * @param result - Object type. For using this object, need cast it.
     */
    void resultProcess(Object result);
}
