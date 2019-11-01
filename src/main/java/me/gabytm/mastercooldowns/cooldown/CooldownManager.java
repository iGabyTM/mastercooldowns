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

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class CooldownManager {
    private List<Cooldown> cooldownsList = new LinkedList<>();

    /**
     * Load all cooldowns from the database
     * @param list source
     */
    public void loadCooldowns(List<Cooldown> list) {
        cooldownsList.addAll(list);
    }

    /**
     * Get the cooldownsList list
     * @return cooldownsList
     */
    public List<Cooldown> getCooldownsList() {
        return cooldownsList;
    }

    /**
     * Get the cooldowns list of a specific player
     * @param uuid player uuid
     * @return list
     */
    public List<Cooldown> getPlayerCooldowns(UUID uuid) {
        List<Cooldown> list = new LinkedList<>();

        for (Cooldown cd : cooldownsList) {
            if (cd.getPlayerUuid().equals(uuid)) list.add(cd);
        }

        return list;
    }

    /**
     * Get the active cooldowns list of a specific player
     * @param uuid player uuid
     * @return list
     */
    public List<Cooldown> getPlayerActiveCooldowns(UUID uuid) {
        List<Cooldown> list = new LinkedList<>();

        for (Cooldown cd : cooldownsList) {
            if (cd.getPlayerUuid().equals(uuid) && !cd.isExpired()) list.add(cd);
        }

        return list;
    }

    /**
     * Get a cooldown of a specific player by name
     * @param uuid player uuid
     * @param name cooldown name
     * @return {@link Cooldown}
     */
    public Cooldown getCooldownByName(UUID uuid, String name) {
        for (Cooldown cd : cooldownsList) {
            if (cd.getPlayerUuid().equals(uuid) && cd.getName().equalsIgnoreCase(name)) return cd;
        }

        return null;
    }

    /**
     * Create a new cooldown and add it to the cooldownsList list
     * @param uuid player uuid
     * @param name cooldown name
     * @param start cooldown start time
     * @param expiration cooldown duration
     */
    public void addCooldown(UUID uuid, String name, long start, long expiration) {
        Cooldown cd = getCooldownByName(uuid, name);

        if (cd == null) {
            cooldownsList.add(new Cooldown(uuid, name.toUpperCase(), start, expiration));
        } else {
            getCooldownByName(uuid, name).setStart(start);
            getCooldownByName(uuid, name).setExpiration(expiration);
        }
    }

    /**
     * Add a cooldown to the cooldownsList list
     * @param cooldown the cooldown object
     */
    public void addCooldown(Cooldown cooldown) {
        UUID uuid = cooldown.getPlayerUuid();
        String name = cooldown.getName();
        Cooldown cd = getCooldownByName(uuid, name);

        if (cd == null) {
            cooldownsList.add(cooldown);
        } else {
            getCooldownByName(uuid, name).setStart(cooldown.getStart());
            getCooldownByName(uuid, name).setExpiration(cooldown.getExpiration());
        }
    }

    /**
     * Remove a specific cooldown from the cooldownsList list
     * @param uuid player uuid
     * @param name cooldown name
     */
    public void removeCooldown(UUID uuid, String name) {
        getCooldownByName(uuid, name).setExpiration(System.currentTimeMillis() / 1000L);
    }

    /**
     * Remove a specific cooldown
     * @param cooldown object
     */
    public void removeCooldown(Cooldown cooldown) {
        cooldown.setExpiration(System.currentTimeMillis() / 1000L);
    }

    /**
     * Rename an existent cooldown
     * @param uuid player uuid
     * @param name cooldown name
     * @param newName cooldown new name
     */
    public void renameCooldown(UUID uuid, String name, String newName) {
        Cooldown cooldown = getCooldownByName(uuid, name);

        if (!cooldownsList.contains(cooldown)) return;

        cooldownsList.remove(cooldown);
        cooldown.setName(newName);
        cooldownsList.add(cooldown);
    }
}