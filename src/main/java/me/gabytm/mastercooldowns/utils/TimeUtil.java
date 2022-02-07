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

import me.gabytm.mastercooldowns.MasterCooldowns;
import org.bukkit.configuration.file.FileConfiguration;

public class TimeUtil {

    private static final MasterCooldowns plugin = MasterCooldowns.getPlugin(MasterCooldowns.class);

    private static void append(final StringBuilder builder, final long duration, final String prefix) {
        if (duration == 0) {
            return;
        }

        if (builder.length() > 0) {
            builder.append(' ');
        }

        builder.append(duration).append(prefix);
    }

    public static String format(final long time) {
        final FileConfiguration config = plugin.getConfig();

        if (time < 60) return time + config.getString("placeholders.formatted.days", "d");

        final long seconds = time;
        final long minutes = seconds / 60;
        final long hours = minutes / 60;
        final long days = hours / 24;

        final StringBuilder builder = new StringBuilder();
        append(builder, days, config.getString("placeholders.formatted.days", "d"));
        append(builder, hours % 24, config.getString("placeholders.formatted.hours", "h"));
        append(builder, minutes % 60, config.getString("placeholders.formatted.minutes", "m"));
        append(builder, seconds % 60, config.getString("placeholders.formatted.seconds", "s"));

        return builder.toString();
    }
}