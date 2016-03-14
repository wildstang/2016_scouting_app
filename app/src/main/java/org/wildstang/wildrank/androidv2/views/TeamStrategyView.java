package org.wildstang.wildrank.androidv2.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import org.wildstang.wildrank.androidv2.R;

/**
 * Created by Janine on 3/13/2016.
 */
public class TeamStrategyView extends LinearLayout {
    public TeamStrategyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_view_team_strategy_box, this, true);
        //inflater.inflate(R.layout.custom_view_note, this, true);
    }
}
