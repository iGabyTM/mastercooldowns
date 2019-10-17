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

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.util.TimeUtil;
import me.gabytm.mastercooldowns.cooldown.Cooldown;
import me.gabytm.mastercooldowns.cooldown.CooldownManager;
import org.bukkit.OfflinePlayer;

import java.util.concurrent.TimeUnit;

public class PlaceholderAPIHook extends PlaceholderExpansion {
    private MasterCooldowns plugin;

    public PlaceholderAPIHook(MasterCooldowns plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "GabyTM";
    }

    @Override
    public String getIdentifier() {
        return "mcd";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer p, String param){
        CooldownManager cooldownManager = plugin.getCooldownManager();

        if (param.toUpperCase().startsWith("LEFT_FORMATTED_")) {
            String[] input = param.toUpperCase().split("LEFT_FORMATTED_");

            if (input.length >= 2) {
                Cooldown cooldown = cooldownManager.getCooldownByName(p.getUniqueId().toString(), input[1]);

                if (cooldown == null) return "0";

                long left = cooldown.getExpiration() * 1000L - System.currentTimeMillis();

                return left <= 0 ? "0" : TimeUtil.getTime((int) TimeUnit.MILLISECONDS.toSeconds( left));
            }

            return "0";
        }

        if (param.toUpperCase().startsWith("LEFT_")) {
            String[] input = param.toUpperCase().split("LEFT_");

            if (input.length >= 2) {
                Cooldown cooldown = cooldownManager.getCooldownByName(p.getUniqueId().toString(), input[1]);

                return cooldown != null ? String.valueOf(cooldown.getTimeLeft()) : "0";
            }

            return "0";
        }

        return null;
    }
}
