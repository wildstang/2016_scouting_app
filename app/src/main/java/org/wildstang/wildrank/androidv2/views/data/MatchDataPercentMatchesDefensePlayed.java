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
public class MatchDataPercentMatchesDefensePlayed extends MatchDataView implements IMatchDataView {
    public MatchDataPercentMatchesDefensePlayed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }
        double matchesDefensePlayed = 0;
        double matches = documents.size();
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            int isDef = (boolean) data.get("teleop-playedDefense") == true ? 1 : 0;
            matchesDefensePlayed += isDef;
        }
        if (matchesDefensePlayed == 0 && matches == 0) {
            setValueText("N/A");
        } else {
            double percentage = (matchesDefensePlayed / (matches));
            setValueText(formatPercentageAsString(percentage));
        }
    }
}
