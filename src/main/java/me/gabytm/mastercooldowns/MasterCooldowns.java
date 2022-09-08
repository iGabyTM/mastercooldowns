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

import me.gabytm.mastercooldowns.api.MasterCooldownsAPI;
import me.gabytm.mastercooldowns.commands.*;
import me.gabytm.mastercooldowns.cooldown.CooldownManager;
import me.gabytm.mastercooldowns.database.DatabaseManager;
import me.gabytm.mastercooldowns.utils.Messages;
import me.gabytm.mastercooldowns.utils.StringUtil;
import me.mattstudios.mf.base.CommandManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class MasterCooldowns extends JavaPlugin {

    private CooldownManager cooldownManager;
    private DatabaseManager databaseManager;
    private BukkitAudiences audiences;

    @Override
    public void onEnable() {
        new Metrics(this, 5794);
        CommandManager commandManager = new CommandManager(this);
        cooldownManager = new CooldownManager();
        databaseManager = new DatabaseManager(this);
        audiences = BukkitAudiences.create(this);

        saveDefaultConfig();
        databaseManager.connect();

        commandManager.register(new MasterCooldownsCommand(this));
        commandManager.register(new AddCooldownCommand(this));
        commandManager.register(new CheckCooldownCommand(this));
        commandManager.register(new ListCooldownsCommand(this));
        commandManager.register(new ReloadCommand(this));
        commandManager.register(new RemoveCooldownCommand(this));

        commandManager.getMessageHandler().register("cmd.no.permission", sender -> sender.sendMessage(Messages.NO_PERMISSION.value()));
        commandManager.getMessageHandler().register("cmd.wrong.usage", sender -> sender.sendMessage(Messages.INCORRECT_USAGE.value()));
        commandManager.getMessageHandler().register("cmd.no.exists", sender -> sender.sendMessage(Messages.UNKNOWN_COMMAND.value()));

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null){
            new PlaceholderAPIHook(this).register();
            StringUtil.infoLog(this, "&aPlaceholderAPI hook enabled.");
        }

        getServer().getServicesManager().register(MasterCooldownsAPI.class, new MasterCooldownsAPI(this), this, ServicePriority.Highest);
    }

    @Override
    public void onDisable() {
        databaseManager.saveCooldowns(getCooldownManager().getCooldownsList());
        HandlerList.unregisterAll(this);
        getServer().getScheduler().cancelTasks(this);
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public BukkitAudiences getAudiences() {
        return audiences;
    }

}