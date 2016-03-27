package org.wildstang.wildrank.androidv2.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.views.data.AutoDataView;
import org.wildstang.wildrank.androidv2.views.data.TeleopDataView;

import java.util.List;

public class TeamSummariesAutoFragment extends TeamSummariesFragment {
    AutoDataView view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_teleop, container, false);
        view = (AutoDataView) v.findViewById(R.id.teleopview);
        v.findViewById(R.id.stack_view_help).setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Help");
            builder.setMessage("Stacks highlighted in red were marked as \"Dropped\".\n\nStacks highlighted in blue were marked as \"Not scored\".\n\nEmpty columns represent matches where no data was recorded for this team.");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });
        return v;
    }

    @Override
    public void acceptNewTeamData(String teamKey, Document teamDoc, Document pitDoc, List<Document> matchDocs) {
        view.acceptNewTeamData(matchDocs);
    }
}
