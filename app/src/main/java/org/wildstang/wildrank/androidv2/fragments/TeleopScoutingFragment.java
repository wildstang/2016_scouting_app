package org.wildstang.wildrank.androidv2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.wildstang.wildrank.androidv2.ActiveDefensesChangedEvent;
import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.StrongholdConstants;
import org.wildstang.wildrank.androidv2.views.scouting.ScoutingCounterView;

import java.util.List;

import de.greenrobot.event.EventBus;

public class TeleopScoutingFragment extends ScoutingFragment {

    private ScoutingCounterView portcullisCounter;
    private ScoutingCounterView moatCounter;
    private ScoutingCounterView rampartsCounter;
    private ScoutingCounterView quadRampCounter;
    private ScoutingCounterView sallyportCounter;
    private ScoutingCounterView drawbridgeCounter;
    private ScoutingCounterView roughTerrainCounter;
    private ScoutingCounterView rockWallCounter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scout_teleop, container, false);

        portcullisCounter = (ScoutingCounterView) view.findViewById(R.id.portcullis_count);
        quadRampCounter = (ScoutingCounterView) view.findViewById(R.id.quad_ramp_count);
        drawbridgeCounter = (ScoutingCounterView) view.findViewById(R.id.drawbridge_count);
        sallyportCounter = (ScoutingCounterView) view.findViewById(R.id.sallyport_count);
        rampartsCounter = (ScoutingCounterView) view.findViewById(R.id.ramparts_count);
        moatCounter = (ScoutingCounterView) view.findViewById(R.id.moat_count);
        roughTerrainCounter = (ScoutingCounterView) view.findViewById(R.id.rough_terrain_count);
        rockWallCounter = (ScoutingCounterView) view.findViewById(R.id.rock_wall_count);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);
    }

    public void onEvent(ActiveDefensesChangedEvent event) {
        portcullisCounter.setVisibility(View.GONE);
        quadRampCounter.setVisibility(View.GONE);
        drawbridgeCounter.setVisibility(View.GONE);
        sallyportCounter.setVisibility(View.GONE);
        rampartsCounter.setVisibility(View.GONE);
        moatCounter.setVisibility(View.GONE);
        roughTerrainCounter.setVisibility(View.GONE);
        rockWallCounter.setVisibility(View.GONE);

        List<String> activeDefenses = event.getActiveDefenses();
        for(int i = 0; i < activeDefenses.size(); i++) {
            switch (activeDefenses.get(i)) {
                case StrongholdConstants.PORTCULLIS:
                    portcullisCounter.setVisibility(View.VISIBLE);
                    break;
                case StrongholdConstants.QUAD_RAMP:
                    quadRampCounter.setVisibility(View.VISIBLE);
                    break;
                case StrongholdConstants.DRAWBRIDGE:
                    drawbridgeCounter.setVisibility(View.VISIBLE);
                    break;
                case StrongholdConstants.SALLYPORT:
                    sallyportCounter.setVisibility(View.VISIBLE);
                    break;
                case StrongholdConstants.RAMPARTS:
                    rampartsCounter.setVisibility(View.VISIBLE);
                    break;
                case StrongholdConstants.MOAT:
                    moatCounter.setVisibility(View.VISIBLE);
                    break;
                case StrongholdConstants.ROUGH_TERRAIN:
                    roughTerrainCounter.setVisibility(View.VISIBLE);
                    break;
                case StrongholdConstants.ROCK_WALL:
                    rockWallCounter.setVisibility(View.VISIBLE);
                    break;
            }
        }

    }

}
