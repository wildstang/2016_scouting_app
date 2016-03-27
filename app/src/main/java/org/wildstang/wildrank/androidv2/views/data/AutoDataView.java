package org.wildstang.wildrank.androidv2.views.data;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.couchbase.lite.Document;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AutoDataView extends View {
    private List<Document> matchDocs;

    Paint textPaint, boulderPaint, scaledPaint, scoredPaint, notScoredPaint, outlinePaint, smallTextPaint, idlePaint;


    public AutoDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        textPaint = new Paint();
        textPaint.setTextSize(25);
        textPaint.setColor(Color.BLACK);
        smallTextPaint = new Paint();
        smallTextPaint.setTextSize(15);
        textPaint.setColor(Color.BLACK);
        boulderPaint = new Paint();
        boulderPaint.setColor(Color.DKGRAY);
        outlinePaint = new Paint();
        outlinePaint.setColor(Color.BLACK);
        outlinePaint.setStyle(Paint.Style.STROKE);
        scoredPaint = new Paint();
        scoredPaint.setColor(Color.argb(150, 0, 255, 0));
        notScoredPaint = new Paint();
        notScoredPaint.setColor(Color.argb(150, 255, 0, 0));
        idlePaint = new Paint();
        idlePaint.setColor(Color.argb(150, 0, 0, 255));
    }

    public void acceptNewTeamData(List<Document> matchDocs) {
        if (matchDocs == null || matchDocs.isEmpty()) {
            invalidate();
            return;
        }
        // Sorts the matches by match number
        Collections.sort(matchDocs, new MatchDocumentComparator());

        this.matchDocs = matchDocs;

        invalidate();
    }

    @Override
    public void onDraw(Canvas c) { // code for drawing everything
        if (matchDocs == null || matchDocs.isEmpty()) {
            c.drawText("No data exists for this team.", 100, 100, textPaint);
        } else {
            int matchNum = 0;
            int x;
            c.drawText("H: ", 5, 60, textPaint);
            c.drawText("L: ", 5, 160, textPaint);
            for (Document doc : matchDocs) {
                Map<String, Object> data = (Map<String, Object>) doc.getProperty("data");
                matchNum++;
                x = (getWidth() - 25) * matchNum / matchDocs.size();

            }
        }
    }

    private double scaledAt(double percent, int totalWidth, int matchNum) {
        double oneWidth = (double) totalWidth / (double) matchNum;
        return totalWidth - oneWidth + (oneWidth * percent);
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
