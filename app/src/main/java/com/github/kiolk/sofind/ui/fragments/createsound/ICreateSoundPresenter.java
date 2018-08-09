package com.github.kiolk.sofind.ui.fragments.createsound;

/**
 * Interface of present component of create sound process
 */

public interface ICreateSoundPresenter {

    /**
     * Calling for update information and show example of single message
     */
    void updateExampleForm();

    /**
     * Calling for safe new sofind item on backend
     * @param sofind - String object what contain sofinde message body.
     */
    void saveNewSofind(String sofind);
}


