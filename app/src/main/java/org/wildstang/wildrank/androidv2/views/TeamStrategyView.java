package org.wildstang.wildrank.androidv2.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.Utilities;
import org.wildstang.wildrank.androidv2.models.TeamDocumentsModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class TeamStrategyView extends LinearLayout {
    public TeamStrategyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_view_team_strategy_box, this, true);
    }

    public void populateFromTeamDocuments(TeamDocumentsModel team) {
        List<Document> matchDocs = team.getMatchDocuments();
        // TODO set R.id.team TextView to team number from team.getTeamDocument()
        Integer teamNumber = (Integer) team.getTeamDocument().getProperty("team_number");
        String teamName = (String) team.getTeamDocument().getProperty("nickname");



        if (matchDocs != null) {

            ((TextView) findViewById(R.id.team)).setText(teamNumber + " (" + teamName + ") (" + matchDocs.size() + " Matches w/ Data)");
            int matches = matchDocs.size();

            double highGoalAccuracy = 0;
            double lowGoalAccuracy = 0;
            double averageLowBarCrosses;
            double averagePortcullisCrosses;
            double averageQuadRampCrosses;
            double averageMoatCrosses;
            double averageRampartsCrosses;
            double averageDrawbridgeCrosses;
            double averageSallyportCrosses;
            double averageRockWallCrosses;
            double averageRoughTerrainCrosses;


            int highGoalMade = 0;
            int highGoalMissed = 0;
            int lowGoalMade = 0;
            int lowGoalMissed = 0;
            int lowBarCrosses = 0;
            int portcullisCrosses = 0;
            int portcullisMatches = 0;
            int quadRampCrosses = 0;
            int quadRampMatches = 0;
            int moatCrosses = 0;
            int moatMatches = 0;
            int rampartsCrosses = 0;
            int rampartsMatches = 0;
            int drawbridgeCrosses = 0;
            int drawbridgeMatches = 0;
            int sallyportCrosses = 0;
            int sallyportMatches = 0;
            int rockWallCrosses = 0;
            int rockWallMatches = 0;
            int roughTerrainCrosses = 0;
            int roughTerrainMatches = 0;
            int challenges = 0;
            int scales = 0;


            for (Document d: matchDocs) {
                Map<String, Object> data = (Map<String, Object>)d.getProperty("data");
                highGoalMade += (int) data.get("teleop-highGoalMade");
                highGoalMissed += (int) data.get("teleop-highGoalMissed");

                lowGoalMade += (int) data.get("teleop-lowGoalMade");
                lowGoalMissed += (int) data.get("teleop-lowGoalMissed");


                portcullisMatches += (boolean) data.get("defense-portcullis") ? 1 : 0;
                quadRampMatches += (boolean) data.get("defense-quadRamp") ? 1 : 0;
                moatMatches += (boolean) data.get("defense-moat") ? 1 : 0;
                rampartsMatches += (boolean) data.get("defense-ramparts") ? 1 : 0;
                drawbridgeMatches += (boolean) data.get("defense-drawbridge") ? 1 : 0;
                sallyportMatches += (boolean) data.get("defense-sallyport") ? 1 : 0;
                rockWallMatches += (boolean) data.get("defense-rockWall") ? 1 : 0;
                roughTerrainMatches += (boolean) data.get("defense-roughTerrain") ? 1 : 0;

                lowBarCrosses += (int) data.get("teleop-lowBar");
                portcullisCrosses += (int) data.get("teleop-portcullis");
                quadRampCrosses += (int) data.get("teleop-quadRamp");
                moatCrosses += (int) data.get("teleop-moat");
                rampartsCrosses += (int) data.get("teleop-ramparts");
                drawbridgeCrosses += (int) data.get("teleop-drawbridge");
                sallyportCrosses += (int) data.get("teleop-sallyport");
                rockWallCrosses += (int) data.get("teleop-rockWall");
                roughTerrainCrosses += (int) data.get("teleop-roughTerrain");

                challenges += (boolean) data.get("teleop-challenged") ? 1 : 0;
                scales += (boolean) data.get("teleop-scaleSuccessful") ? 1 : 0;
            }

            highGoalAccuracy = getAccuracy(highGoalMade, highGoalMissed);
            lowGoalAccuracy = getAccuracy(lowGoalMade, lowGoalMissed);

            averageLowBarCrosses = getAverageCrosses(lowBarCrosses, matches);
            averagePortcullisCrosses = getAverageCrosses(portcullisCrosses, portcullisMatches);
            averageQuadRampCrosses = getAverageCrosses(quadRampCrosses, quadRampMatches);
            averageMoatCrosses = getAverageCrosses(moatCrosses, moatMatches);
            averageRampartsCrosses = getAverageCrosses(rampartsCrosses, rampartsMatches);
            averageDrawbridgeCrosses = getAverageCrosses(drawbridgeCrosses, drawbridgeMatches);
            averageSallyportCrosses = getAverageCrosses(sallyportCrosses, sallyportMatches);
            averageRockWallCrosses = getAverageCrosses(rockWallCrosses, rockWallMatches);
            averageRoughTerrainCrosses = getAverageCrosses(roughTerrainCrosses, roughTerrainMatches);

            ((TextView) findViewById(R.id.high_goal_made)).setText("High Made: " + formatNumberAsString(highGoalMade));
            ((TextView) findViewById(R.id.high_goal_missed)).setText("High Missed: " + formatNumberAsString(highGoalMissed));
            ((TextView) findViewById(R.id.low_goal_made)).setText("Low Made: " + formatNumberAsString(lowGoalMade));
            ((TextView) findViewById(R.id.low_goal_missed)).setText("Low Missed: " + formatNumberAsString(lowGoalMissed));
            ((TextView) findViewById(R.id.average_portcullis)).setText("Avg. Portcullis: " + formatNumberAsString(averagePortcullisCrosses));
            ((TextView) findViewById(R.id.average_quadRamp)).setText("Avg. Quad Ramp: " + formatNumberAsString(averageQuadRampCrosses));
            ((TextView) findViewById(R.id.average_moat)).setText("Avg. Moat: " + formatNumberAsString(averageMoatCrosses));
            ((TextView) findViewById(R.id.average_ramparts)).setText("Avg. Ramparts: " + formatNumberAsString(averageRampartsCrosses));
            ((TextView) findViewById(R.id.average_drawbridge)).setText("Avg. Drawbridge" + formatNumberAsString(averageDrawbridgeCrosses));
            ((TextView) findViewById(R.id.average_sallyport)).setText("Avg. Sallyport: " + formatNumberAsString(averageSallyportCrosses));
            ((TextView) findViewById(R.id.average_rockWall)).setText("Avg. Rock Wall: " + formatNumberAsString(averageRockWallCrosses));
            ((TextView) findViewById(R.id.average_roughTerrain)).setText("Avg. Rock Wall: " + formatNumberAsString(averageRoughTerrainCrosses));
            ((TextView) findViewById(R.id.average_lowBar)).setText("Avg. Low Bar: " + formatNumberAsString(averageLowBarCrosses));
            ((TextView) findViewById(R.id.challenge)).setText("Challenges: " + formatNumberAsString(challenges));
            ((TextView) findViewById(R.id.scales)).setText("Scales: " + formatNumberAsString(scales));
        }  else {
            ((TextView) findViewById(R.id.team)).setText(teamNumber + " (" + teamName + ") (No Matches)");
        }


    }

    private double getAccuracy(int success, int failed) {
        if (success != 0 && failed != 0) {
            return (double) success / (double) (success + failed);
        } else {
            return -1;
        }
    }

    private String formatNumberAsString(double number) {
        return new DecimalFormat("#.##").format(number);
    }

    private String formatPercentageAsString(double percentage) {
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(2);
        return format.format(percentage);
    }

    private double getAverageCrosses(int num, int denom) {
        if (denom != 0) {
            return (double) num / (double) denom;
        } else {
            return -1;
        }
    }

}
