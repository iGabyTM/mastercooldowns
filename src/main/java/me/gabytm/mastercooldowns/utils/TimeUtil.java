/*
 * Copyright 2019 GabyTM
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package me.gabytm.mastercooldowns.utils;

import me.gabytm.mastercooldowns.MasterCooldowns;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.StringJoiner;

public class TimeUtil {

    private static final MasterCooldowns plugin = MasterCooldowns.getPlugin(MasterCooldowns.class);

    private static void append(final StringJoiner joiner, final long duration, final String prefix) {
        if (duration == 0) {
            return;
        }

        joiner.add(duration + prefix);
    }

    public static String format(final long seconds) {
        final FileConfiguration config = plugin.getConfig();

        if (seconds < 60) {
            return seconds + config.getString("placeholders.formatted.seconds", "s");
        }

        final long minutes = seconds / 60;
        final long hours = minutes / 60;
        final long days = hours / 24;

        final StringJoiner joiner = new StringJoiner(" ");
        append(joiner, days, config.getString("placeholders.formatted.days", "d"));
        append(joiner, hours % 24, config.getString("placeholders.formatted.hours", "h"));
        append(joiner, minutes % 60, config.getString("placeholders.formatted.minutes", "m"));
        append(joiner, seconds % 60, config.getString("placeholders.formatted.seconds", "s"));
        return joiner.toString();
    }

}