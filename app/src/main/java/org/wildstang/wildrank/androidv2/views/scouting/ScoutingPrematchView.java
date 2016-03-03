package org.wildstang.wildrank.androidv2.views.scouting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.models.MatchModel;
import org.wildstang.wildrank.androidv2.views.data.ActiveDefenses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 3/1/2016.
 */
public class ScoutingPrematchView extends ScoutingView implements View.OnClickListener {

    public static final String PORTCULLIS = "portcullis";
    public static final String QUAD_RAMP = "quad_ramp";
    public static final String RAMPARTS = "ramparts";
    public static final String SALLYPORT = "sallyport";
    public static final String DRAWBRIDGE = "drawbridge";
    public static final String ROUGH_TERRAIN = "rough_terrain";
    public static final String ROCK_WALL = "rock_wall";
    public static final String MOAT = "moat";

    private ScoutingCheckboxView portcullis;
    private ScoutingCheckboxView quad_ramp;
    private ScoutingCheckboxView ramparts;
    private ScoutingCheckboxView sallyport;
    private ScoutingCheckboxView drawbridge;
    private ScoutingCheckboxView rough_terrain;
    private ScoutingCheckboxView rock_wall;
    private ScoutingCheckboxView moat;

    public ScoutingPrematchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.scouting_view_match, this, true);

        portcullis = (ScoutingCheckboxView) findViewById(R.id.portcullis);
        quad_ramp = (ScoutingCheckboxView) findViewById(R.id.quad_ramp);
        ramparts = (ScoutingCheckboxView) findViewById(R.id.ramparts);
        sallyport = (ScoutingCheckboxView) findViewById(R.id.sallyport);
        drawbridge = (ScoutingCheckboxView) findViewById(R.id.drawbridge);
        rough_terrain = (ScoutingCheckboxView) findViewById(R.id.rough_terrain);
        rock_wall = (ScoutingCheckboxView) findViewById(R.id.rock_wall);
        moat = (ScoutingCheckboxView) findViewById(R.id.moat);
    }

    @Override
    public void onClick(View v) {
        ArrayList<String> defenses = new ArrayList<String>(4);
        if (v.getId() == R.id.done_Pre) {
            int numChecked = 0;
            if (portcullis.isChecked()) {
                numChecked++;
                defenses.add(PORTCULLIS);
            }
            if (portcullis.isChecked()) {
                numChecked++;
                defenses.add(ROCK_WALL);
            }
            if (drawbridge.isChecked()) {
                numChecked++;
                defenses.add(DRAWBRIDGE);
            }
            if (sallyport.isChecked()) {
                numChecked++;
                defenses.add(SALLYPORT);
            }
            if (moat.isChecked()) {
                numChecked++;
                defenses.add(MOAT);
            }
            if (quad_ramp.isChecked()) {
                numChecked++;
                defenses.add(QUAD_RAMP);
            }
            if (ramparts.isChecked()) {
                numChecked++;
                defenses.add(RAMPARTS);
            }
            if (rough_terrain.isChecked()) {
                numChecked++;
                defenses.add(ROUGH_TERRAIN);
            }
            if(numChecked == 4) {
                ActiveDefenses.setDefenses(defenses);
            }
        }
    }

    @Override
    public void writeContentsToMap(Map<String, Object> map) {

    }

    @Override
    public void restoreFromMap(Map<String, Object> map) {

    }


}
