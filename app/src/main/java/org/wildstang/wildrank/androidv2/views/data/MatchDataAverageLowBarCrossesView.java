package org.wildstang.wildrank.androidv2.views.data;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;

import java.nio.DoubleBuffer;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.observables.MathObservable;
import rx.schedulers.Schedulers;

/**
 * Created by Janine on 3/7/2016.
 */
public class MatchDataAverageLowBarCrossesView extends MatchDataView implements IMatchDataView {


    public MatchDataAverageLowBarCrossesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }

//        Observable foulsObservable = Observable.from(documents)
//                .map(doc -> (Map<String, Object>) doc.getProperty("data"))
//                .map(data -> data.get("teleop-lowBar"))
//                .filter(fouls -> fouls != null)
//                .map(fouls -> (int) fouls);
//
//        MathObservable.averageInteger(foulsObservable)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(sum -> setValueText("" + sum), error -> Log.d("wildrank", this.getClass().getName()));
        double lowBarCrosses = 0;
        double matches = 0;
        for (Document document: documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data == null) {
                return;
            }
            matches = documents.size();//Number of Matches?
            lowBarCrosses += (int) data.get("teleop-lowBar");
        }

        if (lowBarCrosses == 0 && matches == 0) {
            setValueText("N/A");
        } else {
            double averageCrosses = (lowBarCrosses / (matches));
            setValueText(formatNumberAsString(averageCrosses));
        }

    }
}
