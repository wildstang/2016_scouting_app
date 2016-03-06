package org.wildstang.wildrank.androidv2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.wildstang.wildrank.androidv2.ActiveDefensesChangedEvent;
import org.wildstang.wildrank.androidv2.R;

import de.greenrobot.event.EventBus;

public class AutonomousScoutingFragment extends ScoutingFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scout_autonomous, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);
    }

    public void onEvent(ActiveDefensesChangedEvent event) {
        Toast.makeText(getContext(), "Active defenses updated!", Toast.LENGTH_LONG).show();
    }
}
