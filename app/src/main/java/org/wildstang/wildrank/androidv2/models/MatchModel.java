package org.wildstang.wildrank.androidv2.models;
// NEEDS UPDATING
import java.util.HashMap;
import java.util.Map;

public class MatchModel {

    public static final String HIGH_GOAL_COUNT_KEY = "high_goal_count";
    public static final String HIGH_GOAL_MISSED_KEY = "high_goal_missed";
    public static final String LOW_GOAL_COUNT_KEY = "low_goal_count";
    public static final String LOW_GOAL_MISSED_KEY = "low_goal_missed";
    public static final String LOW_BAR_CROSSED_KEY = "low_bar_crossed";
    public static final String RAMPARTS_CROSSED_KEY = "ramparts_crossed";
    public static final String PORTCULLIS_CROSSED_KEY = "portcullis_crossed";
    public static final String DRAWBRIDGE_CROSSED_KEY = "drawbridge_crossed";
    public static final String ROUGH_TERRAIN_CROSSED_KEY = "rough_terrain_crossed";
    public static final String MOAT_CROSSED_KEY = "moat_crossed";
    public static final String ROCK_WALL_CROSSED_KEY = "rock_wall_crossed";
    public static final String QUAD_RAMP_CROSSED_KEY = "quad_ramp_crossed";
    public static final String SALLYPORT_CROSSED_KEY = "sallyport_crossed";
    public static final String ATTEMPTED_SCALE_KEY = "attempted_scale";
    public static final String SCALE_SUCCESSFUL_KEY = "scale_successful";
    public static final String CHALLENGED_KEY = "challenged";

    public int highGoalMade;
    public int highGoalMissed;
    public int lowGoalMade;
    public int lowGoalMissed;
    public int low_bar_crossed;
    public int ramparts_crossed;
    public int portcullis_crossed;
    public int drawbridge_crossed;
    public int rough_terrain_crossed;
    public int moat_crossed;
    public int rock_wall_crossed;
    public int quad_ramp_crossed;
    public int sally_port_crossed;
    public boolean attemptedScale;
    public boolean scaleSuccessful;
    public boolean challenged;

    public MatchModel() {
        // Initialize everything to zero/false
        highGoalMade = 0;
        highGoalMissed = 0;
        lowGoalMade = 0;
        lowGoalMissed = 0;
        low_bar_crossed = 0;
        ramparts_crossed = 0;
        portcullis_crossed = 0;
        drawbridge_crossed = 0;
        rough_terrain_crossed = 0;
        moat_crossed = 0;
        rock_wall_crossed = 0;
        quad_ramp_crossed = 0;
        sally_port_crossed = 0;
        attemptedScale = false;
        scaleSuccessful = false;
        challenged = false;
    }

    public static MatchModel fromMap(Map<String, Object> map) {
        MatchModel match = new MatchModel();
        match.highGoalMade = (Integer) map.get(HIGH_GOAL_COUNT_KEY);
        match.highGoalMissed = (Integer) map.get(HIGH_GOAL_MISSED_KEY);
        match.lowGoalMade = (Integer) map.get(LOW_GOAL_COUNT_KEY);
        match.lowGoalMissed = (Integer) map.get(LOW_GOAL_MISSED_KEY);
        match.low_bar_crossed = (Integer) map.get(LOW_BAR_CROSSED_KEY);
        match.ramparts_crossed = (Integer) map.get(RAMPARTS_CROSSED_KEY);
        match.portcullis_crossed = (Integer) map.get(PORTCULLIS_CROSSED_KEY);
        match.drawbridge_crossed = (Integer) map.get(DRAWBRIDGE_CROSSED_KEY);
        match.rough_terrain_crossed = (Integer) map.get(ROUGH_TERRAIN_CROSSED_KEY);
        match.moat_crossed = (Integer) map.get(MOAT_CROSSED_KEY);
        match.rock_wall_crossed = (Integer) map.get(ROCK_WALL_CROSSED_KEY);
        match.quad_ramp_crossed = (Integer) map.get(QUAD_RAMP_CROSSED_KEY);
        match.sally_port_crossed = (Integer) map.get(SALLYPORT_CROSSED_KEY);
        match.attemptedScale = (Boolean) map.get(ATTEMPTED_SCALE_KEY);
        match.scaleSuccessful = (Boolean) map.get(SCALE_SUCCESSFUL_KEY);
        match.challenged = (Boolean) map.get(CHALLENGED_KEY);
        return match;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(MatchModel.HIGH_GOAL_COUNT_KEY, this.highGoalMade);
        map.put(MatchModel.HIGH_GOAL_MISSED_KEY, this.highGoalMissed);
        map.put(MatchModel.LOW_GOAL_COUNT_KEY, this.lowGoalMade);
        map.put(MatchModel.LOW_GOAL_MISSED_KEY, this.lowGoalMissed);
        map.put(MatchModel.LOW_BAR_CROSSED_KEY, this.low_bar_crossed);
        map.put(MatchModel.RAMPARTS_CROSSED_KEY, this.ramparts_crossed);
        map.put(MatchModel.PORTCULLIS_CROSSED_KEY, this.portcullis_crossed);
        map.put(MatchModel.DRAWBRIDGE_CROSSED_KEY, this.drawbridge_crossed);
        map.put(MatchModel.ROUGH_TERRAIN_CROSSED_KEY, this.rough_terrain_crossed);
        map.put(MatchModel.MOAT_CROSSED_KEY, this.moat_crossed);
        map.put(MatchModel.ROCK_WALL_CROSSED_KEY, this.rock_wall_crossed);
        map.put(MatchModel.QUAD_RAMP_CROSSED_KEY, this.quad_ramp_crossed);
        map.put(MatchModel.SALLYPORT_CROSSED_KEY, this.sally_port_crossed);
        map.put(MatchModel.ATTEMPTED_SCALE_KEY, this.attemptedScale);
        map.put(MatchModel.SCALE_SUCCESSFUL_KEY, this.scaleSuccessful);
        map.put(MatchModel.CHALLENGED_KEY, this.challenged);
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MatchModel)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        MatchModel comparing = (MatchModel) o;
        boolean equals = true;
        equals &= (comparing.highGoalMade == this.highGoalMade);
        equals &= (comparing.highGoalMissed == this.highGoalMissed);
        equals &= (comparing.lowGoalMade == this.lowGoalMade);
        equals &= (comparing.lowGoalMissed == this.lowGoalMissed);
        equals &= (comparing.low_bar_crossed == this.low_bar_crossed);
        equals &= (comparing.ramparts_crossed == this.ramparts_crossed);
        equals &= (comparing.portcullis_crossed == this.portcullis_crossed);
        equals &= (comparing.drawbridge_crossed == this.drawbridge_crossed);
        equals &= (comparing.rough_terrain_crossed == this.rough_terrain_crossed);
        equals &= (comparing.moat_crossed == this.moat_crossed);
        equals &= (comparing.rock_wall_crossed == this.rock_wall_crossed);
        equals &= (comparing.quad_ramp_crossed == this.quad_ramp_crossed);
        equals &= (comparing.sally_port_crossed == this.sally_port_crossed);
        equals &= (comparing.attemptedScale == this.attemptedScale);
        equals &= (comparing.scaleSuccessful == this.scaleSuccessful);
        equals &= (comparing.challenged == this.challenged);
        return equals;
    }

    /**
     * A meaningful stack is defined as one that indicates something actually happened.
     * <p>
     * We will define a meaningful stack as one that has totes, a bin, or both.
     *
     * @return true if it is meaningful stack, false if otherwise
     */
    public boolean isMeaningfulStack() {
        return true;
    }
}
