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

package me.gabytm.mastercooldowns;

import me.gabytm.mastercooldowns.commands.*;
import me.gabytm.mastercooldowns.cooldown.CooldownManager;
import me.gabytm.mastercooldowns.utils.Messages;
import me.gabytm.mastercooldowns.database.DatabaseManager;
import me.gabytm.mastercooldowns.utils.StringUtil;
import me.mattstudios.mf.base.CommandManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class MasterCooldowns extends JavaPlugin {
    private CooldownManager cooldownManager;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this);
        CommandManager commandManager = new CommandManager(this);
        cooldownManager = new CooldownManager();
        databaseManager = new DatabaseManager(this);

        metrics.addCustomChart(new Metrics.SingleLineChart("cooldowns", () -> cooldownManager.getCooldownsList().size()));

        databaseManager.connect();

        commandManager.register(new MasterCooldownsCommand());
        commandManager.register(new AddCooldownCommand(this));
        commandManager.register(new CheckCooldownCommand(this));
        commandManager.register(new HelpCommand());
        commandManager.register(new ListCooldownsCommand(this));
        commandManager.register(new RemoveCooldownCommand(this));

        commandManager.getMessageHandler().register("cmd.no.permission", sender -> sender.sendMessage(Messages.NO_PERMISSION.value()));
        commandManager.getMessageHandler().register("cmd.wrong.usage", sender -> sender.sendMessage(Messages.INCORRECT_USAGE.value()));
        commandManager.getMessageHandler().register("cmd.no.exists", sender -> sender.sendMessage(Messages.UNKNOWN_COMMAND.value()));

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new PlaceholderAPIHook(this).register();
            getLogger().info(StringUtil.colorize("&aPlaceholderAPI hook enabled."));
        }
    }

    @Override
    public void onDisable() {
        databaseManager.saveCooldowns(getCooldownManager().getCooldownsList(), getCooldownManager());
        HandlerList.unregisterAll(this);
        getServer().getScheduler().cancelTasks(this);
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}