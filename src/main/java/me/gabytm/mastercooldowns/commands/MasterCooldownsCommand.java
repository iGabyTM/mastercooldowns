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
import me.gabytm.mastercooldowns.utils.StringUtil;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("mastercooldowns")
@Alias({"cd", "mcd", "mcooldowns", "mcooldown"})
public class MasterCooldownsCommand extends CommandBase {
    private MasterCooldowns plugin;

    public MasterCooldownsCommand(MasterCooldowns plugin) { this.plugin = plugin; }

    @Default
    @Permission("mastercooldowns.access")
    public void onDefaultCommand(CommandSender sender) {
        if (sender instanceof Player) StringUtil.sendHelpMessage((Player) sender);
        else sender.sendMessage(Messages.HELP.value());
    }

    @SubCommand("help")
    @Alias("?")
    @Permission("mastercooldowns.access")
    public void onHelpCommand(CommandSender sender) {
        if (sender instanceof Player) StringUtil.sendHelpMessage((Player) sender);
        else sender.sendMessage(Messages.HELP.value());
    }
}
