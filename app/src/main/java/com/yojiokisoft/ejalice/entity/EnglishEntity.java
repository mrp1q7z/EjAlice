/*
 * Copyright (C) 2014 4jiokiSoft
 *
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this
 * program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.yojiokisoft.ejalice.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 英語のエンティティ
 */
@DatabaseTable(tableName = "english")
public class EnglishEntity {
    /**
     * ID
     */
    public final static String ID = "id";
    @DatabaseField(id = true, columnName = ID, canBeNull = false)
    public Long id;

    /**
     * 英語
     */
    public final static String ENGLISH = "english";
    @DatabaseField(columnName = ENGLISH, canBeNull = false)
    public String english;
}
