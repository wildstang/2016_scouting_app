package org.wildstang.wildrank.androidv2.interfaces;

import java.util.Map;

public interface IScoutingView {
    void writeContentsToMap(Map<String, Object> map);

    void restoreFromMap(final Map<String, Object> map);

    String getKey();

    void checkHasKey() throws RuntimeException;

    boolean isComplete(boolean highlight);
}
