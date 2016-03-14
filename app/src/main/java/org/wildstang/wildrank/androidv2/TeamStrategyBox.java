package org.wildstang.wildrank.androidv2;

import android.content.Context;
import android.content.res.Configuration;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Janine on 3/13/2016.
 */
public class TeamStrategyBox {
    LinearLayout layout;
    TextView highGoalAccuracy;
    TextView lowGoalAccuracy;
    View mid;

    public TeamStrategyBox(View v, String team) {
        layout = (LinearLayout) v;
        mid = v.findViewById(R.id.midborder);
        mid.setVisibility(View.GONE);
        highGoalAccuracy = (TextView) v.findViewById(R.id.high_goal_accuracy);
        //highGoalAccuracy.setMovementMethod(new ScrollingMovementMethod());
        //lowGoalAccuracy = (TextView) v.findViewById(R.id.low_goal_accuracy);
        //lowGoalAccuracy.setMovementMethod(new ScrollingMovementMethod());
        ((TextView) v.findViewById(R.id.team)).setText("Team: " + team);
    }

    public void setHighGoalAccuracy(String text, Context c) {
        mid.setVisibility(View.VISIBLE);
        highGoalAccuracy.setVisibility(View.VISIBLE);
        highGoalAccuracy.setText("");
        Configuration configuration = c.getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;
        highGoalAccuracy.setWidth(screenWidthDp / 3);
        highGoalAccuracy.append(text);
    }

    public void setLowGoalAccuracy(String text, Context c) {
        mid.setVisibility(View.VISIBLE);
        lowGoalAccuracy.setVisibility(View.VISIBLE);
        lowGoalAccuracy.setText("");
        Configuration configuration = c.getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;
        lowGoalAccuracy.setWidth(screenWidthDp / 3);
        lowGoalAccuracy.append(text);
    }
}
