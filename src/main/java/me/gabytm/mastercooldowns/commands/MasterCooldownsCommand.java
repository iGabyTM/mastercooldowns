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
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static net.kyori.adventure.text.Component.text;

@Command("mastercooldowns")
@Alias({"cd", "mcd", "mcooldowns", "mcooldown"})
public class MasterCooldownsCommand extends CommandBase {

    private MasterCooldowns plugin;
    private Component helpMessage;

    public MasterCooldownsCommand(MasterCooldowns plugin) {
        this.plugin = plugin;

        try (final InputStreamReader reader = new InputStreamReader(plugin.getResource("commands.yml"))) {
            final PluginDescriptionFile description = plugin.getDescription();

            final YamlConfiguration yaml = YamlConfiguration.loadConfiguration(reader);
            final Component header = MiniMessage.miniMessage().deserialize(
                    String.format(yaml.getString("header"), description.getVersion(), String.join(", ", description.getAuthors()))
            );
            final List<Component> commands = new ArrayList<>();

            for (final String section : yaml.getConfigurationSection("commands").getKeys(false)) {
                final String commandDescription = yaml.getString("commands." + section + ".description");
                final String hover = String.join("<br>", yaml.getStringList("commands." + section + ".hover"));
                final String command = yaml.getString("commands." + section + ".command");

                commands.add(
                        MiniMessage.miniMessage().deserialize(commandDescription)
                                .hoverEvent(MiniMessage.miniMessage().deserialize(hover))
                                .clickEvent(ClickEvent.runCommand(command))
                );
            }

            this.helpMessage = text().append(header).append(commands).build();
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not load the help message from commands.yml", e);
            this.helpMessage = text("Could not load the help message from commands.yml");
        }
    }

    @Default
    @Permission("mastercooldowns.access")
    public void onDefaultCommand(CommandSender sender) {
        plugin.getAudiences().sender(sender).sendMessage(helpMessage);
    }

    @SubCommand("help")
    @Alias("?")
    @Permission("mastercooldowns.access")
    public void onHelpCommand(CommandSender sender) {
        plugin.getAudiences().sender(sender).sendMessage(helpMessage);
    }

}
