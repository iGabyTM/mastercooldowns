
/*
 *
 * PlaceholderAPI
 * Copyright (C) 2019 Ryan McCarthy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package me.gabytm.mastercooldowns.utils;

public class TimeUtil {
    public static String getTime(int seconds) {
        if (seconds < 60) return seconds + "s";

        int minutes = seconds / 60;
        int s = 60 * minutes;
        int secondsLeft = seconds - s;

        if (minutes < 60) return secondsLeft > 0 ? minutes + "m " + secondsLeft + "s" : minutes + "m";

        String time;
        int days;
        int inMins;
        int leftOver;

        if (minutes < 1440) {
            days = minutes / 60;
            time = days + "h";
            inMins = 60 * days;
            leftOver = minutes - inMins;
            if (leftOver >= 1) {
                time = time + " " + leftOver + "m";
            }

            if (secondsLeft > 0) {
                time = time + " " + secondsLeft + "s";
            }

            return time;
        }

        days = minutes / 1440;
        time = days + "d";
        inMins = 1440 * days;
        leftOver = minutes - inMins;

        if (leftOver >= 1) {
            if (leftOver < 60) {
                time = time + " " + leftOver + "m";
            } else {
                int hours = leftOver / 60;
                time = time + " " + hours + "h";
                int hoursInMins = 60 * hours;
                int minsLeft = leftOver - hoursInMins;
                time = time + " " + minsLeft + "m";
            }
        }

        if (secondsLeft > 0) time = time + " " + secondsLeft + "s";

        return time;
    }
}
