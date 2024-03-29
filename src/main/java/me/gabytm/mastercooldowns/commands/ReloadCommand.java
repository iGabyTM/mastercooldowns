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
import me.gabytm.mastercooldowns.utils.Messages;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;

@Command("mastercooldowns")
@Alias({"cd", "mcd", "mcooldowns", "mcooldown"})
public class ReloadCommand extends CommandBase {
    private MasterCooldowns plugin;

    public ReloadCommand(MasterCooldowns plugin) { this.plugin = plugin; }

    @SubCommand("reload")
    @Permission("mastercooldowns.access")
    public void onCommand(CommandSender sender) {
        try {
            plugin.reloadConfig();
            sender.sendMessage(Messages.RELOAD_INFO.value());
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage(Messages.RELOAD_ERROR.value());
        }
    }
}