package org.wildstang.wildrank.androidv2.views.scouting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.models.MatchModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoutingStacksView extends ScoutingView implements View.OnClickListener {

    private List<MatchModel> match = new ArrayList<>();

    private ScoutingCounterView highGoalMadeCounter;
    private ScoutingCounterView highGoalMissedCounter;
    private ScoutingCounterView lowGoalMadeCounter;
    private ScoutingCounterView lowGoalMissedCounter;
    private ScoutingCounterView defense1Counter;
    private ScoutingCounterView defense2Counter;
    private ScoutingCounterView defense3Counter;
    private ScoutingCounterView defense4Counter;
    private ScoutingCounterView lowBarCounter;
    private ScoutingCheckboxView isPlayingDefenseCheckbox;
    private ScoutingCounterView shotsBlockedCounter;
//  private ScoutingSpinnerView preexistingHeightSpinner;
    private ScoutingCheckboxView attemptedScaleCheckBox;
    private ScoutingCheckboxView scaleSuccessfulCheckBox;
    private ScoutingCheckboxView challengeCheckBox;

    public ScoutingStacksView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.scouting_view_stacks, this, true);

        highGoalMadeCounter = (ScoutingCounterView) findViewById(R.id.tote_counter);
        highGoalMissedCounter = (ScoutingCounterView) findViewById(R.id.preexisting);
        lowGoalMadeCounter = (ScoutingCounterView) findViewById(R.id.preexisting_height);
        lowGoalMissedCounter = (ScoutingCounterView) findViewById(R.id.includes_bin);
        defense1Counter = (ScoutingCounterView) findViewById(R.id.preexisting_height);
        defense2Counter = (ScoutingCounterView) findViewById(R.id.preexisting_height);
        defense3Counter = (ScoutingCounterView) findViewById(R.id.preexisting_height);
        defense4Counter = (ScoutingCounterView) findViewById(R.id.preexisting_height);
        lowBarCounter = (ScoutingCounterView) findViewById(R.id.preexisting_height);
        isPlayingDefenseCheckbox = (ScoutingCheckboxView) findViewById(R.id.includes_noodle);
        shotsBlockedCounter = (ScoutingCounterView) findViewById(R.id.preexisting_height);
        attemptedScaleCheckBox = (ScoutingCheckboxView) findViewById(R.id.stack_dropped);
        scaleSuccessfulCheckBox = (ScoutingCheckboxView) findViewById(R.id.bin_dropped);
        challengeCheckBox = (ScoutingCheckboxView) findViewById(R.id.not_scored);

//        preexistingStackCheckbox.setOnValueChangedListener(preexistingHeightSpinner::setEnabled);

        // Synchronize the state of the preexisting height spinner with the checkbox
//        preexistingHeightSpinner.setEnabled(preexistingStackCheckbox.isChecked());

        findViewById(R.id.done).setOnClickListener(this);
    }

    @Override
    public void writeContentsToMap(Map<String, Object> map) {
        List<Map<String, Object>> mappedDataList = new ArrayList<>();
        for (MatchModel stack : match) {
            mappedDataList.add(stack.toMap());
        }
        map.put(key, mappedDataList);
    }

    @Override
    public void restoreFromMap(Map<String, Object> map) {
        List<Map<String, Object>> mappedDataList;
        try {
            mappedDataList = (List<Map<String, Object>>) map.get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }

        if (mappedDataList != null) {
            match.clear();
            for (Map<String, Object> dataMap : mappedDataList) {
                match.add(MatchModel.fromMap(dataMap));
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.done) {
            MatchModel data = new MatchModel();
            data.highGoalMade = highGoalMadeCounter.getCount();
            data.highGoalMissed = highGoalMissedCounter.getCount();
            data.lowGoalMade = lowGoalMadeCounter.getCount();
            data.lowGoalMissed = lowGoalMissedCounter.getCount();
            data.low_bar_crossed = lowBarCounter.getCount();
            data.quad_ramp_crossed = defense1Counter.getCount(); //Will also be replaced by getActiveDefenseCrossed
            data.portcullis_crossed = defense2Counter.getCount();
            data.ramparts_crossed = defense3Counter.getCount();
            data.moat_crossed = defense4Counter.getCount();
            data.attemptedScale = attemptedScaleCheckBox.isChecked();
            data.scaleSuccessful = scaleSuccessfulCheckBox.isChecked();
            data.challenged = challengeCheckBox.isChecked();

            match.add(data);

            // Reset all the views by creating a default StackData
            updateViewsFromData(new MatchModel());
        }
    }

    private void updateViewsFromData(MatchModel data) {
        highGoalMadeCounter.setCount(data.highGoalMade);
        highGoalMissedCounter.setCount(data.highGoalMissed);
        lowGoalMadeCounter.setCount(data.lowGoalMade);
        lowGoalMissedCounter.setCount(data.lowGoalMissed); // THESE WILL CALL getActiveDefenseCrossed(index)
        defense1Counter.setCount(data.quad_ramp_crossed);  // once that method has good code
        defense2Counter.setCount(data.portcullis_crossed);
        defense3Counter.setCount(data.ramparts_crossed);
        defense4Counter.setCount(data.moat_crossed);
        lowBarCounter.setCount(data.low_bar_crossed);
        //shotsBlockedCounter.setCount(data.toteCount);
        attemptedScaleCheckBox.setChecked(data.attemptedScale);
        scaleSuccessfulCheckBox.setChecked(data.scaleSuccessful);
        challengeCheckBox.setChecked(data.challenged);
    }

    private int getActiveDefenseCrossed(int index) { //Will eventually get the active defenses used during the match
        return 0;
    }


}
