package org.wildstang.wildrank.androidv2.views.data;


import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;

import java.util.List;
import java.util.Map;

public class MatchDataLowGoalAutoAccuracy extends MatchDataView implements IMatchDataView {

    public MatchDataLowGoalAutoAccuracy(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }
        double highGoalMade = 0;
        double highGoalMissed = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data == null) {
                return;
            }
            highGoalMissed = (int) data.get("auto-highGoalMissed");
            highGoalMade += (int) data.get("auto-highGoalMade");;
        }
        if (highGoalMade == 0 && highGoalMissed == 0) {
            setValueText("N/A");
        } else {
            double percentage = (highGoalMade / (highGoalMade + highGoalMissed));
            setValueText(formatPercentageAsString(percentage));
        }

    }
}
