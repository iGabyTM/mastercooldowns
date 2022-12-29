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
import me.gabytm.mastercooldowns.cooldown.Cooldown;
import me.gabytm.mastercooldowns.cooldown.CooldownManager;
import me.gabytm.mastercooldowns.utils.TimeUtil;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class PlaceholderAPIHook extends PlaceholderExpansion {

    private final MasterCooldowns plugin;

    public PlaceholderAPIHook(MasterCooldowns plugin) {
        this.plugin = plugin;
    }

    private @NotNull Stream<@Nullable Cooldown> getCooldowns(@NotNull final UUID uuid, @NotNull final String list) {
        return Arrays.stream(list.split(" "))
                .map(it -> plugin.getCooldownManager().getCooldownByName(uuid, it));
    }

    private @NotNull String bool(final boolean bool) {
        return plugin.getConfig().getString("placeholders.boolean." + bool, Boolean.toString(bool));
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getIdentifier() {
        return "mcd";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer p, String param) {
        final CooldownManager cooldownManager = plugin.getCooldownManager();

        if (param.toLowerCase().startsWith("left_formatted_")) {
            final String name = param.toLowerCase().split("left_formatted_")[1];
            final Cooldown cooldown = cooldownManager.getCooldownByName(p.getUniqueId(), name);

            if (cooldown != null && !cooldown.isExpired()) {
                final long left = TimeUnit.SECONDS.toMillis(cooldown.getExpiration()) - System.currentTimeMillis();
                return TimeUtil.format(TimeUnit.MILLISECONDS.toSeconds(left));
            }

            return plugin.getConfig().getString("placeholders.formatted.empty", "");
        }

        if (param.toLowerCase().startsWith("left_")) {
            final String name = param.toLowerCase().split("left_")[1];
            final Cooldown cooldown = cooldownManager.getCooldownByName(p.getUniqueId(), name);

            if (cooldown != null && !cooldown.isExpired()) return String.valueOf(cooldown.getTimeLeft());

            return plugin.getConfig().getString("placeholders.empty", "");
        }

        if (param.startsWith("active_")) {
            final long count = getCooldowns(p.getUniqueId(), param.replace("active_", ""))
                    .filter(Objects::nonNull)
                    .filter(it -> !it.isExpired())
                    .count();
            return String.valueOf(count);
        }

        if (param.startsWith("inactive_")) {
            final long count = getCooldowns(p.getUniqueId(), param.replace("inactive_", ""))
                    .filter(it -> it == null || it.isExpired())
                    .count();
            return String.valueOf(count);
        }

        if (param.startsWith("isactive_")) {
            final Cooldown cooldown = cooldownManager.getCooldownByName(p.getUniqueId(), param.replace("isactive_", ""));
            return bool(cooldown != null && !cooldown.isExpired());
        }

        if (param.startsWith("isinactive_")) {
            final Cooldown cooldown = cooldownManager.getCooldownByName(p.getUniqueId(), param.replace("isinactive_", ""));
            return bool(cooldown == null || cooldown.isExpired());
        }

        return null;
    }

}