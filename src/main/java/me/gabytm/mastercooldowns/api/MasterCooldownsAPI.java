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

package me.gabytm.mastercooldowns.api;

import me.gabytm.mastercooldowns.MasterCooldowns;
import me.gabytm.mastercooldowns.cooldown.Cooldown;

import java.util.List;
import java.util.UUID;

public class MasterCooldownsAPI {
    private MasterCooldowns plugin;

    /**
     * Add a cooldown to the cooldownsList list
     * @param cooldown the cooldown object
     */
    public void addCooldown(Cooldown cooldown) {
        plugin.getCooldownManager().addCooldown(cooldown);
    }

    /**
     * Create a new cooldown and add it to the cooldownsList list
     * @param uuid player uuid
     * @param name cooldown name
     * @param start cooldown start time
     * @param expiration cooldown duration
     */
    public void addCooldown(UUID uuid, String name, long start, long expiration) {
        plugin.getCooldownManager().addCooldown(uuid, name, start, expiration);
    }

    /**
     * Add a cooldown to the cooldownsList list
     * @param cooldown the cooldown object
     */
    public void removeCooldown(Cooldown cooldown) {
        plugin.getCooldownManager().removeCooldown(cooldown);
    }

    /**
     * Remove a specific cooldown from the cooldownsList list
     * @param uuid player uuid
     * @param name cooldown name
     */
    public void removeCooldown(UUID uuid, String name) {
        plugin.getCooldownManager().removeCooldown(uuid, name);
    }

    /**
     * Get the cooldowns list of a specific player
     * @param uuid player uuid
     * @return list
     */
    public List<Cooldown> getPlayerCooldowns(UUID uuid) {
        return plugin.getCooldownManager().getPlayerCooldowns(uuid);
    }

    /**
     * Get the active cooldowns list of a specific player
     * @param uuid player uuid
     * @return list
     */
    public List<Cooldown> getPlayerActiveCooldowns(UUID uuid) {
        return plugin.getCooldownManager().getPlayerActiveCooldowns(uuid);
    }

    /**
     * Get the cooldownsList list
     * @return cooldownsList
     */
    public List<Cooldown> getCooldownsList() {
        return plugin.getCooldownManager().getCooldownsList();
    }
}