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
import me.rayzr522.jsonmessage.JSONMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StringUtil {
    private static String format(String input, Player player) {
        JavaPlugin PLUGIN = JavaPlugin.getProvidingPlugin(MasterCooldowns.class);

        return colorize(input.replaceAll("\\{player}", player.getName()).replaceAll("\\{version}", PLUGIN.getDescription().getVersion()));
    }

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    public static void infoLog(MasterCooldowns plugin, String text) { plugin.getLogger().info(colorize(text)); }

    public static void sendHelpMessage(Player player) {
        String header = format("&cMasterCooldowns &fv{version} &fby &cGabyTM\n", player);

        String addCommand = colorize(" &c/mcd add &f[player] [id┃*┃**] [duration]");
        String addTooltip = format("&8&l ! &7Add a new cooldown, if the id is already in use the cooldown will be overridden, use &f* &7to select all online players or &f** &7for all offline players. The duration is in seconds, it also support the following formats:\n\n" +
                "&8 • &7Second: &fs, sec, secs, second, seconds\n" +
                "&8 • &7Minute: &fm, min, mins, minute, minutes\n" +
                "&8 • &7Hour: &fh, hr, hrs, hour, hours\n" +
                "&8 • &7Day: &fd, dy, dys, day, days\n" +
                "&8 • &7Week: &fw, week, weeks\n" +
                "&8 • &7Month: &fmo, mon, mnth, month, months,\n" +
                "&8 • &7Year: &fy, yr, yrs, year, years\n\n" +
                "&c/mcd add &f{player} example 5400\n" +
                "&c/mcd add &f{player} example 1h30m\n" +
                "&c/mcd add &f* example 10m\n", player);

        String removeCommand = colorize(" &c/mcd remove &f[player┃*┃**] [id┃all]");
        String removeTooltip = format("&8&l ! &7Use &f* &7to select all online players or &f** &7for all offline players and &fall &7as id to select all cooldowns\n\n" +
                "&c/mcd remove &f{player} example\n" +
                "&c/mcd remove &f{player} all\n" +
                "&c/mcd remove &f* example\n" +
                "&c/mcd remove &f** all", player);

        String checkCommand = colorize(" &c/mcd check &f[player] [id]");
        String checkTooltip = format("&8&l ! &7Check the time left of a cooldown\n\n" +
                "&c/mcd check &f{player} example", player);

        String listCommand = colorize(" &c/mcd list &f[player]");
        String listTooltip = format("&8&l ! &7A list of the active cooldowns\n\n" +
                "&c/mcd list &f{player}", player);

        String reloadCommand = colorize(" &c/mcd reload");
        String reloadTooltip = colorize("&8&l ! &7Reload the config\n\n" +
                "&c/mcd reload");

        String helpCommand = colorize(" &c/mcd help");
        String helpTooltip = colorize("&8&l ! &7Display the commands list\n\n" +
                "&c/mcd help\n" +
                "&c/mcd ?");

        JSONMessage.create(header).send(player);
        JSONMessage.create(addCommand).tooltip(addTooltip).suggestCommand("/mcd add ").send(player);
        JSONMessage.create(removeCommand).tooltip(removeTooltip).suggestCommand("/mcd remove ").send(player);
        JSONMessage.create(checkCommand).tooltip(checkTooltip).suggestCommand("/mcd check ").send(player);
        JSONMessage.create(listCommand).tooltip(listTooltip).suggestCommand(format("/mcd list {player}", player)).send(player);
        JSONMessage.create(reloadCommand).tooltip(reloadTooltip).suggestCommand("/mcd reload").send(player);
        JSONMessage.create(helpCommand).tooltip(helpTooltip).suggestCommand("/mcd help").send(player);
    }

    public static void severLog(MasterCooldowns plugin, String text) { plugin.getLogger().severe(colorize(text)); }
}