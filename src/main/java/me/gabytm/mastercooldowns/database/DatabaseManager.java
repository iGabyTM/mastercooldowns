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

package me.gabytm.mastercooldowns.database;

import me.gabytm.mastercooldowns.MasterCooldowns;
import me.gabytm.mastercooldowns.cooldown.Cooldown;
import me.gabytm.mastercooldowns.utils.StringUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DatabaseManager {
    private MasterCooldowns plugin;
    private String uri;
    public DatabaseManager(MasterCooldowns plugin){
        this.plugin = plugin;
    }

    /**
     * Create the plugin data folder if it doesnt exists
     * Create the database file and return the uri
     */
    private void createDatabase() {
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();

        File file = new File(plugin.getDataFolder(), "database.db");

        if (!file.exists()) {
            try {
                file.createNewFile();
                StringUtil.infoLog(plugin, "&cNo database found, creating one...");
            } catch (IOException e) {
                e.printStackTrace();
                StringUtil.severLog(plugin, "&cAn error occurred while creating the database.");
            }
        }

        this.uri = "jdbc:sqlite:" + file.toPath().toString();
    }

    /**
     * Setup the database connection
     */
    public void connect() {
        createDatabase();

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(uri);

            if (connection != null) {
                createTable(connection);
                plugin.getCooldownManager().loadCooldowns(loadCooldowns());

                new BukkitRunnable() {
                    public void run() {
                        saveCooldowns(plugin.getCooldownManager().getCooldownsList());
                    }
                }.runTaskTimerAsynchronously(plugin, 12000L, 12000L);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the 'cooldowns' database table
     * @param connection {@link Connection}
     */
    private void createTable(Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(Queries.CREATE_TABLE.value());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the data from database and store it on a map
     * @return cooldownsList
     */
    private List<Cooldown> loadCooldowns() {
        List<Cooldown> cooldownsList = new LinkedList<>();

        try {
            Connection connection = DriverManager.getConnection(uri);

            if (connection != null) {
                PreparedStatement select = connection.prepareStatement(Queries.LOAD_SELECT.value());

                select.execute();

                ResultSet selectResult = select.getResultSet();

                while (selectResult.next()) {
                    UUID uuid = UUID.fromString(selectResult.getString("uuid"));
                    String name = selectResult.getString("name");
                    long start = selectResult.getLong("start");
                    long expiration = selectResult.getLong("expiration");

                    if (expiration <= System.currentTimeMillis() / 1000L) {
                        PreparedStatement delete = connection.prepareStatement(Queries.LOAD_DELETE.value());

                        delete.setString(1, uuid.toString());
                        delete.setString(2, name);
                        delete.executeUpdate();
                        delete.close();
                        continue;
                    }

                    Cooldown cooldown = new Cooldown(uuid, name, start, expiration);

                    cooldownsList.add(cooldown);
                }

                if (cooldownsList.size() == 0) StringUtil.infoLog(plugin, "&cNo cooldowns found.");
                else StringUtil.infoLog(plugin, cooldownsList.size() + " &acooldown(s) have been loaded.");

                select.close();
                return cooldownsList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            StringUtil.severLog(plugin, "&cAn error occurred while loading the cooldowns.");
        }

        return cooldownsList;
    }

    /**
     * Save the cooldowns to the database and remove the expired ones
     * @param cooldowns cooldowns list
     */
    public void saveCooldowns(List<Cooldown> cooldowns){
        if (cooldowns.size() == 0) return;

        try {
            Connection connection = DriverManager.getConnection(uri);
            List<Cooldown> expiredCooldowns = new LinkedList<>();

            for (Cooldown cd : cooldowns) {
                if (connection != null) {
                    if (cd.isExpired()) {
                        PreparedStatement delete = connection.prepareStatement(Queries.SAVE_DELETE.value());

                        delete.setString(1, cd.getPlayerUuid().toString());
                        delete.setString(2, cd.getName());
                        delete.executeUpdate();
                        delete.close();
                        expiredCooldowns.add(cd);
                        continue;
                    }

                    PreparedStatement check = connection.prepareStatement(Queries.SAVE_CHECK.value());

                    check.setString(1, cd.getPlayerUuid().toString());
                    check.setString(2, cd.getName());
                    check.execute();

                    ResultSet checkResult = check.getResultSet();

                    if (!checkResult.next()) {
                        PreparedStatement insert = connection.prepareStatement(Queries.SAVE_INSERT.value());

                        insert.setString(1, cd.getPlayerUuid().toString());
                        insert.setString(2, cd.getName());
                        insert.setLong(3, cd.getStart());
                        insert.setLong(4, cd.getExpiration());
                        insert.executeUpdate();
                        insert.close();
                        continue;
                    }

                    PreparedStatement update = connection.prepareStatement(Queries.SAVE_UPDATE.value());

                    update.setLong(1, cd.getStart());
                    update.setLong(2, cd.getExpiration());
                    update.setString(3, cd.getPlayerUuid().toString());
                    update.setString(4, cd.getName());
                    update.executeUpdate();
                    update.close();
                    check.close();
                }
            }

            cooldowns.removeAll(expiredCooldowns);

            if (cooldowns.size() > 0) StringUtil.infoLog(plugin, "&aSaving &f" + cooldowns.size() + " &acooldown(s) to database.");
            else StringUtil.infoLog(plugin,"&aSaving cooldowns to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}