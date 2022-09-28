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

package me.gabytm.mastercooldowns.cooldown;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CooldownManager {

    private final Table<UUID, String, Cooldown> loadedCooldowns = HashBasedTable.create();

    /**
     * Load all cooldowns from the database
     *
     * @param cooldowns source
     */
    public void loadCooldowns(@NotNull final Table<UUID, String, Cooldown> cooldowns) {
        loadedCooldowns.putAll(cooldowns);
    }

    /**
     * Get the cooldownsList list
     *
     * @return cooldownsList
     */
    public @NotNull Table<UUID, String, Cooldown> getLoadedCooldowns() {
        return loadedCooldowns;
    }

    /**
     * Get the cooldowns list of a specific player
     *
     * @param uuid player uuid
     * @return list
     */
    public @NotNull Collection<Cooldown> getPlayerCooldowns(@NotNull final UUID uuid) {
        return loadedCooldowns.row(uuid).values();
    }

    /**
     * Get the active cooldowns list of a specific player
     *
     * @param uuid player uuid
     * @return list
     */
    public @NotNull List<Cooldown> getPlayerActiveCooldowns(@NotNull final UUID uuid) {
        return getPlayerCooldowns(uuid)
                .stream()
                .filter(it -> !it.isExpired())
                .collect(Collectors.toList());
    }

    /**
     * Get a cooldown of a specific player by name
     *
     * @param uuid player uuid
     * @param name cooldown name
     * @return {@link Cooldown}
     */
    public @Nullable Cooldown getCooldownByName(@NotNull final UUID uuid, @NotNull final String name) {
        return getPlayerCooldowns(uuid)
                .stream()
                .filter(it -> it.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Create a new cooldown and add it to the cooldownsList list
     *
     * @param uuid       player uuid
     * @param name       cooldown name
     * @param start      cooldown start time
     * @param expiration cooldown duration
     */
    public void addCooldown(@NotNull final UUID uuid, @NotNull final String name, final long start, final long expiration) {
        final Cooldown cooldown = getCooldownByName(uuid, name);

        if (cooldown == null) {
            loadedCooldowns.put(uuid, name, new Cooldown(uuid, name.toUpperCase(), start, expiration));
        } else {
            cooldown.setStart(start);
            cooldown.setExpiration(expiration);
        }
    }

    /**
     * Add a cooldown to the cooldownsList list
     *
     * @param original the cooldown object
     */
    public void addCooldown(@NotNull final Cooldown original) {
        final Cooldown cooldown = getCooldownByName(original.getPlayerUuid(), original.getName());

        if (cooldown == null) {
            loadedCooldowns.put(original.getPlayerUuid(), original.getName(), original);
        } else {
            cooldown.setStart(original.getStart());
            cooldown.setExpiration(original.getExpiration());
        }
    }

    /**
     * Remove a specific cooldown from the cooldownsList list
     *
     * @param uuid player uuid
     * @param name cooldown name
     */
    public void removeCooldown(@NotNull final UUID uuid, @NotNull final String name) {
        final Cooldown cooldown = getCooldownByName(uuid, name);

        if (cooldown != null) {
            removeCooldown(cooldown);
        }
    }

    /**
     * Remove a specific cooldown
     *
     * @param cooldown object
     */
    public void removeCooldown(@NotNull final Cooldown cooldown) {
        cooldown.setExpiration(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
    }

    /**
     * Rename an existent cooldown
     *
     * @param uuid    player uuid
     * @param name    cooldown name
     * @param newName cooldown new name
     */
    public void renameCooldown(@NotNull final UUID uuid, @NotNull final String name, @NotNull final String newName) {
        final Cooldown cooldown = getCooldownByName(uuid, name);

        if (cooldown != null) {
            cooldown.setName(newName);
        }
    }

}