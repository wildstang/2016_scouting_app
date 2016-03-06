package org.wildstang.wildrank.androidv2.views.data;


import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;

import java.util.List;
import java.util.Map;

public class MatchDataLowGoalAccuracy extends MatchDataView implements IMatchDataView {

    public MatchDataLowGoalAccuracy(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }
        double lowGoalMade = 0;
        double lowGoalMissed = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data == null) {
                return;
            }
            lowGoalMissed += (int) data.get("teleop-lowGoalMissed");
            lowGoalMade += (int) data.get("teleop-lowGoalMade");
        }
        if (lowGoalMade == 0 && lowGoalMissed == 0) {
            setValueText("N/A");
        } else {
            double percentage = (lowGoalMade / (lowGoalMade + lowGoalMissed));
            setValueText(formatPercentageAsString(percentage));
        }

    }
}
