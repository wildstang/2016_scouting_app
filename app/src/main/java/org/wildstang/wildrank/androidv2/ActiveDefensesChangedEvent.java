package org.wildstang.wildrank.androidv2;

import java.util.List;

/**
 * Created by Janine on 3/5/2016.
 */
public class ActiveDefensesChangedEvent {

    private List<String> mActiveDefenses;

    public ActiveDefensesChangedEvent(List<String> activeDefenses) {
        mActiveDefenses = activeDefenses;
    }

    public List<String> getActiveDefenses() {
        return mActiveDefenses;
    }
}
