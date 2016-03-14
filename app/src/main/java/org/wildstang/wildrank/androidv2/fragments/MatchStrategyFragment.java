package org.wildstang.wildrank.androidv2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.NoteBox;
import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.TeamStrategyBox;
import org.wildstang.wildrank.androidv2.Utilities;
import org.wildstang.wildrank.androidv2.adapters.TeamSummariesFragmentPagerAdapter;
import org.wildstang.wildrank.androidv2.data.DatabaseManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Janine on 3/13/2016.
 */
public class MatchStrategyFragment extends Fragment {
    public List<TeamStrategyBox> boxes = new ArrayList<>();
    String[] teams;
    View section;

    public static MatchStrategyFragment newInstance(String[] teams) {
        MatchStrategyFragment f = new MatchStrategyFragment();
        Bundle b = new Bundle();
        b.putStringArray("teams", teams);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            teams = getArguments().getStringArray("teams");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match_strategy, container, false);
        boxes.add(new TeamStrategyBox(view.findViewById(R.id.one), Integer.toString(Utilities.teamNumberFromTeamKey(teams[0]))));
        boxes.add(new TeamStrategyBox(view.findViewById(R.id.two), Integer.toString(Utilities.teamNumberFromTeamKey(teams[1]))));
        boxes.add(new TeamStrategyBox(view.findViewById(R.id.three), Integer.toString(Utilities.teamNumberFromTeamKey(teams[2]))));
        boxes.add(new TeamStrategyBox(view.findViewById(R.id.four), Integer.toString(Utilities.teamNumberFromTeamKey(teams[3]))));
        boxes.add(new TeamStrategyBox(view.findViewById(R.id.five), Integer.toString(Utilities.teamNumberFromTeamKey(teams[4]))));
        boxes.add(new TeamStrategyBox(view.findViewById(R.id.six), Integer.toString(Utilities.teamNumberFromTeamKey(teams[5]))));

        loadInfoForTeam(teams[3]);
        return view;
    }

    private void loadInfoForTeam(String teamKey) {
        try {
            DatabaseManager db = DatabaseManager.getInstance(getContext());
            Document teamDocument = db.getTeamFromKey(teamKey);
            Document pitDocument = db.getInternalDatabase().getExistingDocument("pit:" + teamKey);
            List<Document> matchDocuments = db.getMatchResultsForTeam(teamKey);
            boxes.get(0).setHighGoalAccuracy(((Integer)(((Map<String, Object>) matchDocuments.get(0).getProperty("data")).get("teleop-highGoalMade"))).toString(), getContext());
        } catch (CouchbaseLiteException | IOException e) {
            e.printStackTrace();
            //Toast.makeText(this, "Error loading data for team. Check LogCat.", Toast.LENGTH_LONG).show();
        }
    }

}
