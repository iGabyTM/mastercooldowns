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

package me.gabytm.mastercooldowns.commands;

import me.gabytm.mastercooldowns.MasterCooldowns;
import me.gabytm.mastercooldowns.cooldown.Cooldown;
import me.gabytm.mastercooldowns.cooldown.CooldownManager;
import me.gabytm.mastercooldowns.utils.Messages;
import me.gabytm.mastercooldowns.utils.NumberUtil;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("mastercooldowns")
@Alias({"cd", "mcd", "mcooldowns", "mcooldown"})
public class AddCooldownCommand extends CommandBase {
    private MasterCooldowns plugin;
    public AddCooldownCommand(MasterCooldowns plugin) {
        this.plugin = plugin;
    }

    @SubCommand("add")
    @Completion("#players")
    @Permission("mastercooldowns.access")
    public void onCommand(CommandSender sender, String to, String cdName, String cdDuration) {
        CooldownManager cooldownManager = plugin.getCooldownManager();
        long duration = System.currentTimeMillis() / 1000L + NumberUtil.durationToSeconds(cdDuration);

        boolean onlineSendMessages = plugin.getConfig().getBoolean("settings.add.all.onlineSendMessages", true);
        boolean offlineSendMessages = plugin.getConfig().getBoolean("settings.add.all.offlineSendMessages", true);

        if (to.equals("*")) {
            if (sender.getServer().getOnlinePlayers().size() == 0) {
                sender.sendMessage(Messages.NO_ONLINE_PLAYERS.value());
                return;
            }

            for (Player p : sender.getServer().getOnlinePlayers()) {
                addCooldown(cooldownManager, sender, p, cdName, duration, onlineSendMessages);
            }

            return;
        }

        if (to.equals("**")) {
            if (sender.getServer().getOfflinePlayers().length == 0) {
                sender.sendMessage(Messages.NO_OFFLINE_PLAYERS.value());
                return;
            }

            for (OfflinePlayer p : sender.getServer().getOfflinePlayers()) {
                addCooldown(cooldownManager, sender, p, cdName, duration, offlineSendMessages);
            }

            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(to);

        addCooldown(cooldownManager, sender, target, cdName, duration, plugin.getConfig().getBoolean("settings.add.single.sendMessage", true));
    }

    private void addCooldown(CooldownManager cooldownManager, CommandSender sender, OfflinePlayer p, String name, long duration, boolean sendMessages) {
        Cooldown cooldown = new Cooldown(p.getUniqueId(), name, System.currentTimeMillis() / 1000L, duration);
        cooldownManager.addCooldown(cooldown);

        if (sendMessages) sender.sendMessage(Messages.ADD_INFO.format(cooldown));
    }
}