package org.kaismh.solr;

/**
 * Created by kaismh on 7/24/16.
 */
public class Utils {

    public static boolean isBlank(final String value) {

        if (value == null || (value.trim().length()) == 0) {
            return true;
        } else {
            return false;
        }

    }
}
