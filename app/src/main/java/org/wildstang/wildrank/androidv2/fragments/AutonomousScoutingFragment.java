package org.wildstang.wildrank.androidv2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.wildstang.wildrank.androidv2.ActiveDefensesChangedEvent;
import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.StrongholdConstants;
import org.wildstang.wildrank.androidv2.views.scouting.ScoutingCheckboxView;
import org.wildstang.wildrank.androidv2.views.scouting.ScoutingCounterView;

import java.util.List;

import de.greenrobot.event.EventBus;

public class AutonomousScoutingFragment extends ScoutingFragment {

    private ScoutingCounterView portcullisCounter;
    private ScoutingCounterView moatCounter;
    private ScoutingCounterView rampartsCounter;
    private ScoutingCounterView quadRampCounter;
    private ScoutingCounterView sallyportCounter;
    private ScoutingCounterView drawbridgeCounter;
    private ScoutingCounterView roughTerrainCounter;
    private ScoutingCounterView rockWallCounter;

    private ScoutingCheckboxView portcullisReach;
    private ScoutingCheckboxView moatReach;
    private ScoutingCheckboxView rampartsReach;
    private ScoutingCheckboxView quadRampReach;
    private ScoutingCheckboxView sallyportReach;
    private ScoutingCheckboxView drawbridgeReach;
    private ScoutingCheckboxView roughTerrainReach;
    private ScoutingCheckboxView rockWallReach;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scout_autonomous, container, false);

        portcullisCounter = (ScoutingCounterView) view.findViewById(R.id.auto_portcullis_count);
        quadRampCounter = (ScoutingCounterView) view.findViewById(R.id.auto_quad_ramp_count);
        drawbridgeCounter = (ScoutingCounterView) view.findViewById(R.id.auto_drawbridge_count);
        sallyportCounter = (ScoutingCounterView) view.findViewById(R.id.auto_sallyport_count);
        rampartsCounter = (ScoutingCounterView) view.findViewById(R.id.auto_ramparts_count);
        moatCounter = (ScoutingCounterView) view.findViewById(R.id.auto_moat_count);
        roughTerrainCounter = (ScoutingCounterView) view.findViewById(R.id.auto_rough_terrain_count);
        rockWallCounter = (ScoutingCounterView) view.findViewById(R.id.auto_rock_wall_count);

        portcullisReach = (ScoutingCheckboxView) view.findViewById(R.id.auto_portcullis_reach);
        quadRampReach = (ScoutingCheckboxView) view.findViewById(R.id.auto_quad_ramp_reach);
        drawbridgeReach = (ScoutingCheckboxView) view.findViewById(R.id.auto_drawbridge_reach);
        sallyportReach = (ScoutingCheckboxView) view.findViewById(R.id.auto_sallyport_reach);
        rampartsReach = (ScoutingCheckboxView) view.findViewById(R.id.auto_ramparts_reach);
        moatReach = (ScoutingCheckboxView) view.findViewById(R.id.auto_moat_reach);
        roughTerrainReach = (ScoutingCheckboxView) view.findViewById(R.id.auto_rough_terrain_reach);
        rockWallReach = (ScoutingCheckboxView) view.findViewById(R.id.auto_rock_wall_reach);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);
    }

    public void onEvent(ActiveDefensesChangedEvent event) {
        Toast.makeText(getContext(), "Active defenses updated!", Toast.LENGTH_LONG).show();
        portcullisCounter.setVisibility(View.GONE);
        quadRampCounter.setVisibility(View.GONE);
        drawbridgeCounter.setVisibility(View.GONE);
        sallyportCounter.setVisibility(View.GONE);
        rampartsCounter.setVisibility(View.GONE);
        moatCounter.setVisibility(View.GONE);
        roughTerrainCounter.setVisibility(View.GONE);
        rockWallCounter.setVisibility(View.GONE);

        portcullisReach.setVisibility(View.GONE);
        quadRampReach.setVisibility(View.GONE);
        drawbridgeReach.setVisibility(View.GONE);
        sallyportReach.setVisibility(View.GONE);
        rampartsReach.setVisibility(View.GONE);
        moatReach.setVisibility(View.GONE);
        roughTerrainReach.setVisibility(View.GONE);
        rockWallReach.setVisibility(View.GONE);

        List<String> activeDefenses = event.getActiveDefenses();
        for(int i = 0; i < activeDefenses.size(); i++) {
            switch (activeDefenses.get(i)) {
                case StrongholdConstants.PORTCULLIS:
                    portcullisCounter.setVisibility(View.VISIBLE);
                    portcullisReach.setVisibility(View.VISIBLE);
                    break;
                case StrongholdConstants.QUAD_RAMP:
                    quadRampCounter.setVisibility(View.VISIBLE);
                    quadRampReach.setVisibility(View.VISIBLE);
                    break;
                case StrongholdConstants.DRAWBRIDGE:
                    drawbridgeCounter.setVisibility(View.VISIBLE);
                    drawbridgeReach.setVisibility(View.VISIBLE);
                    break;
                case StrongholdConstants.SALLYPORT:
                    sallyportCounter.setVisibility(View.VISIBLE);
                    sallyportReach.setVisibility(View.VISIBLE);
                    break;
                case StrongholdConstants.RAMPARTS:
                    rampartsCounter.setVisibility(View.VISIBLE);
                    rampartsReach.setVisibility(View.VISIBLE);
                    break;
                case StrongholdConstants.MOAT:
                    moatCounter.setVisibility(View.VISIBLE);
                    moatReach.setVisibility(View.VISIBLE);
                    break;
                case StrongholdConstants.ROUGH_TERRAIN:
                    roughTerrainCounter.setVisibility(View.VISIBLE);
                    roughTerrainReach.setVisibility(View.VISIBLE);
                    break;
                case StrongholdConstants.ROCK_WALL:
                    rockWallCounter.setVisibility(View.VISIBLE);
                    rockWallReach.setVisibility(View.VISIBLE);
                    break;
            }
        }

    }
}
