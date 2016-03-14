package org.wildstang.wildrank.androidv2.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.Utilities;
import org.wildstang.wildrank.androidv2.fragments.MatchNotesFragment;
import org.wildstang.wildrank.androidv2.fragments.MatchStrategyFragment;

/**
 * Created by Janine on 3/13/2016.
 */
public class MatchStrategyActivity extends AppCompatActivity {

    private static String matchKey;
    private static String[] teamKeys;
    private Toolbar toolbar;
    public static final String TEAM_KEYS = "team_keys";
    public static final String MATCH_KEY = "match_keys";
    private MatchStrategyFragment stratFrag;


    public static Intent createIntent(Context context, String matchKey, String[] teams) {
        Intent i = new Intent(context, MatchStrategyActivity.class);
        i.putExtra(TEAM_KEYS, teams);
        i.putExtra(MATCH_KEY, matchKey);
        return i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        matchKey = extras.getString(MATCH_KEY);
        teamKeys = extras.getStringArray(TEAM_KEYS);

        setContentView(R.layout.activity_match_strategy);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String team = PreferenceManager.getDefaultSharedPreferences(this).getString("assignedTeam", "red_1");
        toolbar.setTitleTextColor(Color.WHITE);
        if (team.contains("red")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.material_red));

        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.material_blue));
        }
        //puts the team numbers of the teams that are being scouted in the toolbar
        ((TextView) findViewById(R.id.match_number)).setText("" + Utilities.matchNumberFromMatchKey(matchKey));
        stratFrag = MatchStrategyFragment.newInstance(teamKeys);//passes in the team keys to the fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.space, stratFrag);
        ft.commit();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            MatchStrategyActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
