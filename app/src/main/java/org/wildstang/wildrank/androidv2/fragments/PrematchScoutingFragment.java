package org.wildstang.wildrank.androidv2.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.wildstang.wildrank.androidv2.ActiveDefensesChangedEvent;
import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.StrongholdConstants;
import org.wildstang.wildrank.androidv2.activities.ScoutMatchActivity;
import org.wildstang.wildrank.androidv2.views.data.ActiveDefenses;
import org.wildstang.wildrank.androidv2.views.scouting.ScoutingCheckboxView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class PrematchScoutingFragment extends ScoutingFragment implements View.OnClickListener {

    private ScoutingCheckboxView portcullis;
    private ScoutingCheckboxView quad_ramp;
    private ScoutingCheckboxView ramparts;
    private ScoutingCheckboxView sallyport;
    private ScoutingCheckboxView drawbridge;
    private ScoutingCheckboxView rough_terrain;
    private ScoutingCheckboxView rock_wall;
    private ScoutingCheckboxView moat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scout_prematch, container, false);

        portcullis = (ScoutingCheckboxView) view.findViewById(R.id.portcullis);
        quad_ramp = (ScoutingCheckboxView) view.findViewById(R.id.quad_ramp);
        ramparts = (ScoutingCheckboxView) view.findViewById(R.id.ramparts);
        sallyport = (ScoutingCheckboxView) view.findViewById(R.id.sallyport);
        drawbridge = (ScoutingCheckboxView) view.findViewById(R.id.drawbridge);
        rough_terrain = (ScoutingCheckboxView) view.findViewById(R.id.rough_terrain);
        rock_wall = (ScoutingCheckboxView) view.findViewById(R.id.rock_wall);
        moat = (ScoutingCheckboxView) view.findViewById(R.id.moat);

        view.findViewById(R.id.done_Pre).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        ArrayList<String> defenses = new ArrayList<String>(4);
        if (v.getId() == R.id.done_Pre) {
            int numChecked = 0;
            if (portcullis.isChecked()) {
                numChecked++;
                defenses.add(StrongholdConstants.PORTCULLIS);
            }
            if (rock_wall.isChecked()) {
                numChecked++;
                defenses.add(StrongholdConstants.ROCK_WALL);
            }
            if (drawbridge.isChecked()) {
                numChecked++;
                defenses.add(StrongholdConstants.DRAWBRIDGE);
            }
            if (sallyport.isChecked()) {
                numChecked++;
                defenses.add(StrongholdConstants.SALLYPORT);
            }
            if (moat.isChecked()) {
                numChecked++;
                defenses.add(StrongholdConstants.MOAT);
            }
            if (quad_ramp.isChecked()) {
                numChecked++;
                defenses.add(StrongholdConstants.QUAD_RAMP);
            }
            if (ramparts.isChecked()) {
                numChecked++;
                defenses.add(StrongholdConstants.RAMPARTS);
            }
            if (rough_terrain.isChecked()) {
                numChecked++;
                defenses.add(StrongholdConstants.ROUGH_TERRAIN);
            }
            if (numChecked == 4) {
                ActiveDefenses.setDefenses(defenses);
                Activity activity = getActivity();
                if (activity instanceof ScoutMatchActivity) {
                    ((ScoutMatchActivity) activity).setSwipeEnabled(true);
                }
                EventBus.getDefault().post(new ActiveDefensesChangedEvent(defenses));
            } else {
                Activity activity = getActivity();
                if (activity instanceof ScoutMatchActivity) {
                    ((ScoutMatchActivity) activity).setSwipeEnabled(false);
                }
                Toast.makeText(getContext(), "Please select exactly 4 defenses!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
