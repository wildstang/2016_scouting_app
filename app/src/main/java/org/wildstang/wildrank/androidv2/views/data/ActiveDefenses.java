package org.wildstang.wildrank.androidv2.views.data;

import java.util.ArrayList;

/**
 * Created by Janine on 3/2/2016.
 */
public class ActiveDefenses {
    private static ArrayList<String> activeDefenses = new ArrayList<String>(4);


    public static void setDefenses(ArrayList<String> a) {
        if (a.size() == 4) {
            for (int i = 0; i < 4; i++) {
                activeDefenses.add(i, a.get(i));
            }
        }
    }

    public static String getDefense(int index) {
        return activeDefenses.get(index);
    }

}
