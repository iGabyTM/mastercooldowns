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
            " &8• &c/mcd add &f[player┃*┃**] [id] [duration]\n" +
            "   &8&o- &7&oAdd a new cooldown, if the id is already in use the cooldown will be overridden. The duration is in seconds\n" +
            "   &8&o- &7&oUse &f&o* &7&oto select all online players or &f&o** &7&ofor all offline players\n" +
            " &8• &c/mcd remove &f[player┃*┃**] [id┃all]\n" +
            "   &8&o- &7&oUse &f&o* &7&oto select all online players or &f&o** &7&ofor all offline players and &f&oall &7&oas id to select all cooldowns\n" +
            " &8• &c/mcd check &f[player] [id]\n" +
            "   &8&o- &7&oCheck the time left of a cooldown\n" +
            " &8• &c/mcd list &f[player]\n" +
            "   &8&o- &7&oA list of the active cooldowns\n" +
            " &8• &c/mcd reload\n" +
            "   &8&o- &7&oReload the config\n" +
            " &8• &c/mcd help\n" +
            "   &8&o- &7&oDisplay the commands list\n \n" +
            "&4Tip! &7Run the help command in-game for more info."),
    INCORRECT_USAGE("{prefix} &7Incorrect usage! Type &c/mcd help &7for help."),
    NO_OFFLINE_PLAYERS("{prefix} &7No offline players found."),
    NO_ONLINE_PLAYERS("{prefix} &7The server is empty!"),
    NO_PERMISSION("{prefix} &cYou don't have access to use this command!"),
    RELOAD_INFO("{prefix} &7The config has been successfully reloaded."),
    RELOAD_ERROR("{prefix} &cAn error occurred while reloading the config!"),
    REMOVE("{prefix} &7The cooldown &8'&c{id}&8' &7has been removed for &c{player}&7."),
    REMOVE_ALL("{prefix} &7Removed all &8(&c{amount}&8) &7cooldowns for &c{player}&7."),
    LIST("&c{id} &8(&f{leftFormatted}&8)"),
    LIST_EMPTY("{prefix} &c{player} &7has no active cooldowns."),
    LIST_HEADER("{prefix} &c{player}'s &7active cooldowns:\n"),
    UNKNOWN_COMMAND("{prefix} &7Unknown command, type &c/mcd help &7for help.");

    private final String value;

    Messages(String value) {
        this.value = StringUtil.colorize(
                value
                        .replace("{prefix}", "&cMCD &8┃")
                        .replace("{version}", JavaPlugin.getProvidingPlugin(MasterCooldowns.class).getDescription().getVersion())
        );
    }

    public String value() {
        return this.value;
    }

    public String format(Cooldown cd) {
        return value()
                .replace("{player}", cd.getPlayer().getName())
                .replace("{id}", cd.getName().toLowerCase())
                .replace("{left}", String.valueOf(cd.getTimeLeft()))
                .replace("{leftFormatted}", cd.getTimeLeftFormatted());
    }

    public String format(int amount, OfflinePlayer player) {
        return value()
                .replace("{amount}", String.valueOf(amount))
                .replace("{player}", player.getName());
    }

    public String format(OfflinePlayer player) {
        return value().replace("{player}", player.getName());
    }

    public String format(String id, OfflinePlayer player) {
        return value()
                .replace("{id}", id)
                .replace("{player}", player.getName());
    }
}