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
public class MatchDataScaleSucessRateView extends MatchDataView implements IMatchDataView {
    public MatchDataScaleSucessRateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }
        double scaleSuccessful = 0;
        double scaleAttempted = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data == null) {
                return;
            }
            int attempted = (boolean) data.get("teleop-attemptedScale") == true ? 1 : 0;
            int success = (boolean) data.get("teleop-scaleSuccessful") == true ? 1 : 0;
            scaleAttempted += attempted ;
            scaleSuccessful += success;
        }
        if (scaleSuccessful == 0 && scaleAttempted == 0) {
            setValueText("N/A");
        } else {
            double percentage = (scaleSuccessful / (scaleAttempted));
            setValueText(formatPercentageAsString(percentage));
        }
    }
}
