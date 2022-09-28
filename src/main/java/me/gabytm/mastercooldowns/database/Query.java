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

enum Query {
    CREATE_TABLE("CREATE TABLE IF NOT EXISTS cooldowns (\n" +
            "     uuid text NOT NULL,\n" +
            "     name text NOT NULL,\n" +
            "     start INTEGER NOT NULL,\n" +
            "     expiration INTEGER NOT NULL,\n" +
            "     PRIMARY KEY (uuid, name)" +
            "     )"),
    LOAD_DELETE("DELETE FROM cooldowns WHERE uuid = ? AND name = ?"),
    LOAD_SELECT("SELECT * FROM cooldowns"),
    SAVE_CHECK("SELECT start FROM cooldowns WHERE uuid = ? AND name = ? LIMIT 1"),
    SAVE_DELETE("DELETE FROM cooldowns WHERE uuid = ? AND name = ?"),
    SAVE_INSERT("INSERT INTO cooldowns (uuid, name, start, expiration) VALUES (?, ?, ?, ?)"),
    SAVE_UPDATE("UPDATE cooldowns SET start = ?, expiration = ? WHERE uuid = ? AND name = ?");

    private final String value;

    Query(String value) { this.value = value; }

    public String value() { return value; }
}