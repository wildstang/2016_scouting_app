package org.wildstang.wildrank.androidv2.views.data;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.models.MatchModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class StackDataView extends View {
    List<Map<String, Object>> matchData;

    Paint textPaint, boulderPaint, scaledPaint, notScoredPaint, outlinePaint;

    public StackDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        boulderPaint = new Paint();
        boulderPaint.setColor(Color.DKGRAY);
        outlinePaint = new Paint();
        outlinePaint.setColor(Color.BLACK);
        outlinePaint.setStyle(Paint.Style.STROKE);
        notScoredPaint = new Paint();
        notScoredPaint.setColor(Color.argb(200, 255, 0, 0)); // Translucent blue


    }

    public void acceptNewTeamData(List<Document> matchDocs) {
        if (matchDocs == null || matchDocs.isEmpty()) {
            return;
        }
        // Sorts the matches by match number
        Collections.sort(matchDocs, new MatchDocumentComparator());

        // Default, empty MatchModel to compare to
        for (Document doc : matchDocs) {
            Map<String, Object> data = (Map<String, Object>) doc.getProperty("data");
            matchData = (List<Map<String, Object>>) data.get("matches");
        }
        invalidate();
    }

    @Override
    public void onDraw(Canvas c) { // code for drawing everything
//        if (matches.isEmpty()) {
//            c.drawText("No data exists for this team.", 100, 100, textPaint);
//        }
//
//
//        int matchNum = 0;
//        int x;
//        for (MatchModel match : matches) {
//            matchNum++;
//            x = getWidth() * matchNum / matches.size();
//
//
//
//            // Draw line to separate matches
//            c.drawLine(x, getHeight() - 50, x * (matchNum + 1) / matchNum, getHeight() - 50, outlinePaint);
//            c.drawLine(getWidth() * matchNum/ matches.size(), 0, getWidth() * matchNum / matches.size(), getHeight(), outlinePaint);
//       }
    }

    class MatchDocumentComparator implements Comparator<Document> {

        @Override
        public int compare(Document lhs, Document rhs) {
            try {
                int lhsMatchNumber = Integer.parseInt(((String) lhs.getProperty("match_key")).replaceAll("[0-9]+[a-zA-Z]+_[a-zA-Z]+", ""));
                int rhsMatchNumber = Integer.parseInt(((String) rhs.getProperty("match_key")).replaceAll("[0-9]+[a-zA-Z]+_[a-zA-Z]+", ""));
                Log.d("wildrank", "lhs: " + lhsMatchNumber + ", rhs: " + rhsMatchNumber);
                return (lhsMatchNumber - rhsMatchNumber);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }
}
