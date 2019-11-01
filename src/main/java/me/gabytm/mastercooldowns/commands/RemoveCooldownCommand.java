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
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@Command("mastercooldowns")
@Alias({"cd", "mcd", "mcooldowns", "mcooldown"})
public class RemoveCooldownCommand extends CommandBase {
    private MasterCooldowns plugin;
    public RemoveCooldownCommand(MasterCooldowns plugin) { this.plugin = plugin; }

    @SubCommand("remove")
    @Completion("#players")
    @Permission("mastercooldowns.access")
    @SuppressWarnings("Duplicates")
    public void onCommand(CommandSender sender, String from, String cdName) {
        CooldownManager cooldownManager = plugin.getCooldownManager();
        boolean onlineSendMessages = plugin.getConfig().getBoolean("settings.remove.all.onlineSendMessages", true);
        boolean offlineSendMessages = plugin.getConfig().getBoolean("settings.remove.all.offlineSendMessages", true);

        // Remove cooldowns from all online players
        if (from.equals("*")) {
            if (sender.getServer().getOnlinePlayers().size() == 0) {
                sender.sendMessage(Messages.NO_ONLINE_PLAYERS.value());
                return;
            }

            // Remove all cooldowns from all online players
            if (cdName.equalsIgnoreCase("all")) {
                for (Player p : sender.getServer().getOnlinePlayers()) {
                    removeAllCooldowns(cooldownManager, p, sender, onlineSendMessages);
                }

                return;
            }

            // Remove the specified cooldown from all online players
            for (Player p : sender.getServer().getOnlinePlayers()) {
                removeCooldown(cooldownManager, p, sender, cdName, onlineSendMessages);
            }

            return;
        }

        // Remove cooldowns from all offline players
        if (from.equals("**")) {
            if (sender.getServer().getOfflinePlayers().length == 0) {
                sender.sendMessage(Messages.NO_OFFLINE_PLAYERS.value());
                return;
            }

            // Remove all cooldowns from all offline players
            if (cdName.equalsIgnoreCase("all")) {
                for (OfflinePlayer p : sender.getServer().getOfflinePlayers()) {
                    removeAllCooldowns(cooldownManager, p, sender, offlineSendMessages);
                }

                return;
            }

            // Remove the specified cooldown from all offline players
            for (OfflinePlayer p : sender.getServer().getOfflinePlayers()) {
                removeCooldown(cooldownManager, p, sender, cdName, offlineSendMessages);
            }

            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(from);

        // Remove all cooldowns from the target
        if (cdName.equalsIgnoreCase("all")) {
            removeAllCooldowns(cooldownManager, target, sender, true);
            return;
        }

        // Remove the specified cooldown from the target
        removeCooldown(cooldownManager, target, sender, cdName, true);
    }

    private void removeCooldown(CooldownManager cooldownManager, OfflinePlayer p, CommandSender s, String name, boolean sendMessages) {
        Cooldown cooldown = cooldownManager.getCooldownByName(p.getUniqueId(), name);

        if (cooldown == null || cooldown.isExpired()) {
            if (sendMessages) s.sendMessage(Messages.COOLDOWN_NOT_FOUND.format(name, p));
            return;
        }

        cooldownManager.removeCooldown(cooldown);
        if (sendMessages) s.sendMessage(Messages.REMOVE.format(cooldown));
    }

    private void removeAllCooldowns(CooldownManager cooldownManager, OfflinePlayer p, CommandSender s, boolean sendMessages) {
        List<Cooldown> cooldowns = cooldownManager.getPlayerActiveCooldowns(p.getUniqueId());

        if (cooldowns.size() == 0) {
            if (sendMessages) s.sendMessage(Messages.LIST_EMPTY.format(p));
            return;
        }

        for (Cooldown cd : cooldowns) {
            cooldownManager.removeCooldown(cd);
        }

        if (sendMessages) s.sendMessage(Messages.REMOVE_ALL.format(cooldowns.size(), p));
    }
}