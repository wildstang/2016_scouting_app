package org.wildstang.wildrank.androidv2.views.scouting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.models.MatchModel;
import org.wildstang.wildrank.androidv2.views.data.ActiveDefenses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoutingRobotView extends ScoutingView implements View.OnClickListener {

    private List<MatchModel> match = new ArrayList<>();

    private ScoutingCounterView highGoalMadeCounter;
    private ScoutingCounterView highGoalMissedCounter;
    private ScoutingCounterView lowGoalMadeCounter;
    private ScoutingCounterView lowGoalMissedCounter;
    private ScoutingCounterView portcullisCounter;
    private ScoutingCounterView moatCounter;
    private ScoutingCounterView rampartsCounter;
    private ScoutingCounterView quadRampCounter;
    private ScoutingCounterView sallyportCounter;
    private ScoutingCounterView drawbridgeCounter;
    private ScoutingCounterView roughTerrainCounter;
    private ScoutingCounterView rockWallCounter;
    private ScoutingCounterView lowBarCounter;
    private ScoutingCheckboxView isPlayingDefenseCheckbox;
    private ScoutingCounterView shotsBlockedCounter;
    private ScoutingCheckboxView attemptedScaleCheckBox;
    private ScoutingCheckboxView scaleSuccessfulCheckBox;
    private ScoutingCheckboxView challengeCheckBox;

    public ScoutingRobotView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.scouting_view_match, this, true);

        highGoalMadeCounter = (ScoutingCounterView) findViewById(R.id.high_goal_made);
        highGoalMissedCounter = (ScoutingCounterView) findViewById(R.id.high_goal_missed);
        lowGoalMadeCounter = (ScoutingCounterView) findViewById(R.id.low_goal_made);
        lowGoalMissedCounter = (ScoutingCounterView) findViewById(R.id.low_goal_missed);
        setActiveCounters();
        lowBarCounter = (ScoutingCounterView) findViewById(R.id.low_bar);
        isPlayingDefenseCheckbox = (ScoutingCheckboxView) findViewById(R.id.defense);
        shotsBlockedCounter = (ScoutingCounterView) findViewById(R.id.shots_blocked);
        isPlayingDefenseCheckbox = (ScoutingCheckboxView) findViewById(R.id.defense);
        shotsBlockedCounter = (ScoutingCounterView) findViewById(R.id.shots_blocked);
        attemptedScaleCheckBox = (ScoutingCheckboxView) findViewById(R.id.scale_attempted);
        scaleSuccessfulCheckBox = (ScoutingCheckboxView) findViewById(R.id.scale_successful);
        challengeCheckBox = (ScoutingCheckboxView) findViewById(R.id.challenge);

//        preexistingStackCheckbox.setOnValueChangedListener(preexistingHeightSpinner::setEnabled);

        // Synchronize the state of the preexisting height spinner with the checkbox
//        preexistingHeightSpinner.setEnabled(preexistingStackCheckbox.isChecked());

        findViewById(R.id.finish).setOnClickListener(this);
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
        if (id == R.id.finish) {
            MatchModel data = new MatchModel();
            data.highGoalMade = highGoalMadeCounter.getCount();
            data.highGoalMissed = highGoalMissedCounter.getCount();
            data.lowGoalMade = lowGoalMadeCounter.getCount();
            data.lowGoalMissed = lowGoalMissedCounter.getCount();
            data.low_bar_crossed = lowBarCounter.getCount();
            for (int i = 0; i < 4; i++) {
                setData(data, i);
            }
            data.isPlayingDefense = isPlayingDefenseCheckbox.isChecked();
            data.shotsBlocked = shotsBlockedCounter.getCount();
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
        for (int i = 0; i < 4; i++) {
            getActiveDefense(i).setCount(getDefenseCrossed(i, data));
        }
        lowBarCounter.setCount(data.low_bar_crossed);
        isPlayingDefenseCheckbox.setChecked(data.isPlayingDefense);
        shotsBlockedCounter.setCount(data.shotsBlocked);
        attemptedScaleCheckBox.setChecked(data.attemptedScale);
        scaleSuccessfulCheckBox.setChecked(data.scaleSuccessful);
        challengeCheckBox.setChecked(data.challenged);
    }

    private ScoutingCounterView getActiveDefense(int index) {
        String name = ActiveDefenses.getDefense(index);
        switch (name) {
            case ScoutingPrematchView.ROCK_WALL:
                return rockWallCounter;
            case ScoutingPrematchView.PORTCULLIS:
                return portcullisCounter;
            case ScoutingPrematchView.QUAD_RAMP:
                return quadRampCounter;
            case ScoutingPrematchView.RAMPARTS:
                return rampartsCounter;
            case ScoutingPrematchView.MOAT:
                return moatCounter;
            case ScoutingPrematchView.DRAWBRIDGE:
                return drawbridgeCounter;
            case ScoutingPrematchView.ROUGH_TERRAIN:
                return roughTerrainCounter;
            case ScoutingPrematchView.SALLYPORT:
                return sallyportCounter;
            default:
                return null;
        }
    }

    private int getDefenseId(int index) {
        String name = ActiveDefenses.getDefense(index);
        switch (name) {
            case ScoutingPrematchView.ROCK_WALL:
                return R.id.rock_wall_count;
            case ScoutingPrematchView.PORTCULLIS:
                return R.id.portcullis_count;
            case ScoutingPrematchView.QUAD_RAMP:
                return R.id.quad_ramp_count;
            case ScoutingPrematchView.RAMPARTS:
                return R.id.ramparts_count;
            case ScoutingPrematchView.MOAT:
                return R.id.moat_count;
            case ScoutingPrematchView.DRAWBRIDGE:
                return R.id.drawbridge_count;
            case ScoutingPrematchView.ROUGH_TERRAIN:
                return R.id.rough_terrain_count;
            case ScoutingPrematchView.SALLYPORT:
                return R.id.sallyport_count;
            default:
                return 0;
        }
    }

    private int getDefenseCrossed(int index, MatchModel data) {
        String name = ActiveDefenses.getDefense(index);
        switch (name) {
            case ScoutingPrematchView.ROCK_WALL:
                return data.rock_wall_crossed;
            case ScoutingPrematchView.PORTCULLIS:
                return data.portcullis_crossed;
            case ScoutingPrematchView.QUAD_RAMP:
                return data.quad_ramp_crossed;
            case ScoutingPrematchView.RAMPARTS:
                return data.ramparts_crossed;
            case ScoutingPrematchView.MOAT:
                return data.moat_crossed;
            case ScoutingPrematchView.DRAWBRIDGE:
                return data.drawbridge_crossed;
            case ScoutingPrematchView.ROUGH_TERRAIN:
                return data.rough_terrain_crossed;
            case ScoutingPrematchView.SALLYPORT:
                return data.sally_port_crossed;
            default:
                return 0;
        }
    }

    private void setData(MatchModel data, int i) {
        String name = ActiveDefenses.getDefense(i);
        if (getActiveDefense(i) != null) {
            switch (name) {
                case ScoutingPrematchView.ROCK_WALL:
                    data.rock_wall_crossed = getActiveDefense(i).getCount();
                    break;
                case ScoutingPrematchView.PORTCULLIS:
                    data.portcullis_crossed = getActiveDefense(i).getCount();
                    break;
                case ScoutingPrematchView.QUAD_RAMP:
                    data.quad_ramp_crossed = getActiveDefense(i).getCount();
                    break;
                case ScoutingPrematchView.RAMPARTS:
                    data.ramparts_crossed = getActiveDefense(i).getCount();
                    break;
                case ScoutingPrematchView.MOAT:
                    data.moat_crossed = getActiveDefense(i).getCount();
                    break;
                case ScoutingPrematchView.DRAWBRIDGE:
                    data.drawbridge_crossed = getActiveDefense(i).getCount();
                    break;
                case ScoutingPrematchView.ROUGH_TERRAIN:
                    data.rough_terrain_crossed = getActiveDefense(i).getCount();
                    break;
                case ScoutingPrematchView.SALLYPORT:
                    data.sally_port_crossed = getActiveDefense(i).getCount();
                    break;
            }
        }
    }
    private void setActiveCounters() {
        for (int i = 0; i < 4; i++) {
            String name = ActiveDefenses.getDefense(i);
            switch(name) {
                case ScoutingPrematchView.PORTCULLIS:
                    portcullisCounter = (ScoutingCounterView) findViewById(R.id.portcullis_count);
                    break;
                case ScoutingPrematchView.QUAD_RAMP:
                    quadRampCounter = (ScoutingCounterView) findViewById(R.id.quad_ramp_count);
                    break;
                case ScoutingPrematchView.DRAWBRIDGE:
                    drawbridgeCounter = (ScoutingCounterView) findViewById(R.id.drawbridge_count);
                    break;
                case ScoutingPrematchView.SALLYPORT:
                    sallyportCounter = (ScoutingCounterView) findViewById(R.id.sallyport_count);
                    break;
                case ScoutingPrematchView.RAMPARTS:
                    rampartsCounter = (ScoutingCounterView) findViewById(R.id.ramparts_count);
                    break;
                case ScoutingPrematchView.MOAT:
                    moatCounter = (ScoutingCounterView) findViewById(R.id.moat_count);
                    break;
                case ScoutingPrematchView.ROUGH_TERRAIN:
                    roughTerrainCounter = (ScoutingCounterView) findViewById(R.id.rough_terrain_count);
                    break;
                case ScoutingPrematchView.ROCK_WALL:
                    rockWallCounter = (ScoutingCounterView) findViewById(R.id.rock_wall_count);
                    break;
            }
        }
    }

}
