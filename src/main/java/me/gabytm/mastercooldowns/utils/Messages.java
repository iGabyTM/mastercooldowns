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
import me.gabytm.mastercooldowns.cooldown.Cooldown;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

public enum Messages {
    ADD_INFO("{prefix} &7A new cooldown &8'&c{id}&8' &7has been added for &c{player}&7, it will expire in &c{leftFormatted}&7."),
    CHECK_INFO("{prefix} &7The cooldown &8'&c{id}&8' &7for &c{player} &7will expire in &c{leftFormatted}."),
    COOLDOWN_NOT_FOUND("{prefix} &7A cooldown named &8'&c{id}&8' &7wasn't found for &c{player}&7."),
    HELP("&cMasterCooldowns &fv{version} &fby &cGabyTM\n \n" +
            " &8* &c/mcd add &f[player] [id] [duration]\n" +
            "   &8&o- &7&oAdd a new cooldown, if the id is already in use the cooldown will be overridden. The duration is in seconds\n" +
            " &8* &c/mcd remove &f[player] [id]\n" +
            "   &8&o- &7&oRemove a cooldown\n" +
            " &8* &c/mcd check &f[player] [id]\n" +
            "   &8&o- &7&oCheck the time left of a cooldown\n" +
            " &8* &c/mcd list &f[player]\n" +
            "   &8&o- &7&oA list of the active cooldowns\n" +
            " &8* &c/mcd help\n" +
            "   &8&o- &7&oDisplay the commands list"),
    INCORRECT_USAGE("{prefix} &7Incorrect usage! Type &c/mcp help &7for help."),
    NO_PERMISSION("{prefix} &cYou don't have access to use this command!"),
    REMOVE("{prefix} &7The cooldown &8'&c{id}&8' &7has been removed for &c{player}&7."),
    LIST("&c{id} &8(&f{leftFormatted}&8)"),
    LIST_EMPTY("{prefix} &c{player} &7has no active cooldowns."),
    LIST_HEADER("{prefix} &c{player}'s &7active cooldowns:\n"),
    UNKNOWN_COMMAND("{prefix} &7Unknown command, type &c/mcp help &7for help.");

    private String value;
    private final String prefix = "&cMCD &8┃";
    private final JavaPlugin PLUGIN = JavaPlugin.getProvidingPlugin(MasterCooldowns.class);

    Messages(String value) {
        this.value = value;
    }

    public String value() {
        return StringUtil.colorize(value.replaceAll("\\{prefix}", prefix).replaceAll("\\{version}", PLUGIN.getDescription().getVersion()));
    }

    public String format(Cooldown cd) {
        String msg = value
                .replaceAll("\\{prefix}", prefix)
                .replaceAll("\\{player}", cd.getPlayer().getName())
                .replaceAll("\\{id}", cd.getName().toLowerCase())
                .replaceAll("\\{left}", String.valueOf(cd.getTimeLeft()))
                .replaceAll("\\{leftFormatted}", cd.getTimeLeftFormatted());
        return StringUtil.colorize(msg);
    }

    public String format(OfflinePlayer player) {
        String msg = value
                .replaceAll("\\{prefix}", prefix)
                .replaceAll("\\{player}", player.getName());
        return StringUtil.colorize(msg);
    }

    public String format(String id, OfflinePlayer player) {
        String msg = value
                .replaceAll("\\{prefix}", prefix)
                .replaceAll("\\{id}", id)
                .replaceAll("\\{player}", player.getName());
        return StringUtil.colorize(msg);
    }
}