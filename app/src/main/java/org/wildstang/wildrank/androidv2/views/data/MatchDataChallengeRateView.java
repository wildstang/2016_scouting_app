package org.wildstang.wildrank.androidv2.views.data;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;

import java.util.List;
import java.util.Map;

/**
 * Created by Janine on 3/8/2016.
 */
public class MatchDataChallengeRateView extends MatchDataView implements IMatchDataView {
    public MatchDataChallengeRateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }
        double matchesChallenged = 0;
        double matches = documents.size();
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            int didChal = (boolean) data.get("teleop-challenged") || (boolean) data.get("teleop-scaleSuccessful") == true ? 1 : 0;

            matchesChallenged += didChal;
        }
        if (matchesChallenged == 0 && matches == 0) {
            setValueText("N/A");
        } else {
            double percentage = (matchesChallenged / (matches));
            setValueText(formatPercentageAsString(percentage));
        }
    }
}
