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
    public List<Cooldown> getCooldowns(String uuid) {
        List<Cooldown> list = new LinkedList<>();

        for (Cooldown cd : cooldownsList) {
            if (cd.getUuid().equals(uuid)) list.add(cd);
        }

        return list;
    }

    /**
     * Get a cooldown of a specific player by name
     * @param uuid player uuid
     * @param name cooldown name
     * @return cd
     */
    public Cooldown getCooldownByName(String uuid, String name) {
        for (Cooldown cd : cooldownsList) {
            if (cd.getUuid().equals(uuid) && cd.getName().equals(name)) return cd;
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
    public void addCooldown(String uuid, String name, long start, long expiration) {
        cooldownsList.remove(getCooldownByName(uuid, name));

        Cooldown cooldown = new Cooldown(uuid, name, start, expiration);

        cooldownsList.add(cooldown);
    }

    /**
     * Add a cooldown to the cooldownsList list
     * @param cooldown the cooldown object
     */
    public void addCooldown(Cooldown cooldown) {
        cooldownsList.remove(getCooldownByName(cooldown.getUuid(), cooldown.getName()));
        cooldownsList.add(cooldown);
    }

    /**
     * Remove a specific cooldown from the cooldownsList list
     * @param uuid player uuid
     * @param name cooldown name
     */
    public void removeCooldown(String uuid, String name) {
        Cooldown cooldown = getCooldownByName(uuid, name);

        cooldownsList.remove(cooldown);
    }

    /**
     * Remove a specific cooldown from the cooldownsList list
     * @param cooldown object
     */
    public void removeCooldown(Cooldown cooldown) {
        cooldownsList.remove(cooldown);
    }

    /**
     * Rename an existent cooldown
     * @param uuid player uuid
     * @param name cooldown name
     * @param newName cooldown new name
     */
    public void renameCooldown(String uuid, String name, String newName) {
        Cooldown cooldown = getCooldownByName(uuid, name);

        if (!cooldownsList.contains(cooldown)) return;

        cooldownsList.remove(cooldown);
        cooldown.setName(newName);
        cooldownsList.add(cooldown);
    }
}