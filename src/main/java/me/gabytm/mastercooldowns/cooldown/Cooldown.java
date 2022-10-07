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

import me.gabytm.mastercooldowns.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Cooldown implements Serializable {
    private UUID uuid;
    private String name;
    private long start;
    private long expiration;

    /**
     * The cooldown object
     * @param uuid player uuid
     * @param name cooldown name
     * @param start cooldown start time
     * @param expiration cooldown expiration time
     */
    public Cooldown(UUID uuid, String name, long start, long expiration) {
        this.uuid = uuid;
        this.name = name;
        this.start = start;
        this.expiration = expiration;
    }

    public Cooldown(UUID uuid, String name, long expiration) {
        this(uuid, name, TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()), expiration);
    }

    /**
     * Get the player uuid
     * @return uuid
     */
    public UUID getPlayerUuid() { return uuid; }

    /**
     * Get the cooldown name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the cooldown start time
     * @return start
     */
    public long getStart() {
        return start;
    }

    /**
     * Get the cooldown expiration time
     * @return expiration
     */
    public long getExpiration() {
        return expiration;
    }

    /**
     * Check if the cooldown is expired
     * @return boolean
     */
    public boolean isExpired() {
        return getExpiration() <= System.currentTimeMillis() / 1000L;
    }

    /**
     * Get the time left of a cooldown
     * @return time left
     */
    public long getTimeLeft() {
        return getExpiration() - System.currentTimeMillis() / 1000L <= 0 ? 0 : getExpiration() - System.currentTimeMillis() / 1000L;
    }

    /**
     * Get the time left of a cooldown formatted using {@link TimeUtil}
     * @return time left
     */
    public String getTimeLeftFormatted() {
        if (getTimeLeft() <= 0) return "0s";

        long left = getExpiration() * 1000L - System.currentTimeMillis();
        return TimeUtil.format((int) TimeUnit.MILLISECONDS.toSeconds(left));
    }

    /**
     * Get the player object of a cooldown
     * @return {@link OfflinePlayer}
     */
    public OfflinePlayer getPlayer() { return Bukkit.getOfflinePlayer(getPlayerUuid()); }

    /**
     * Set the cooldown name
     * @param name new value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set a new star time
     * @param start time
     */
    public void setStart(long start) { this.start = start; }

    /**
     * Set a new expiration time
     * @param expiration new value
     */
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}