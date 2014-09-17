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
 * ページのエンティティ
 */
@DatabaseTable(tableName = "pages")
public class PagesEntity {
    /**
     * ID
     */
    public final static String ID = "id";
    @DatabaseField(id = true, columnName = ID, canBeNull = false)
    public Long id;

    /**
     * 画像
     */
    public final static String IMAGE = "image";
    @DatabaseField(columnName = IMAGE, canBeNull = false, defaultValue = "")
    public String image;

    /**
     * １行目の内容
     */
    public final static String LINE1 = "line1";
    @DatabaseField(columnName = LINE1, canBeNull = false, defaultValue = "0")
    public Integer line1;

    /**
     * ２行目の内容
     */
    public final static String LINE2 = "line2";
    @DatabaseField(columnName = LINE2, canBeNull = false, defaultValue = "0")
    public Integer line2;

    /**
     * ３行目の内容
     */
    public final static String LINE3 = "line3";
    @DatabaseField(columnName = LINE3, canBeNull = false, defaultValue = "0")
    public Integer line3;

    /**
     * ４行目の内容
     */
    public final static String LINE4 = "line4";
    @DatabaseField(columnName = LINE4, canBeNull = false, defaultValue = "0")
    public Integer line4;

    /**
     * ５行目の内容
     */
    public final static String LINE5 = "line5";
    @DatabaseField(columnName = LINE5, canBeNull = false, defaultValue = "0")
    public Integer line5;

    /**
     * ６行目の内容
     */
    public final static String LINE6 = "line6";
    @DatabaseField(columnName = LINE6, canBeNull = false, defaultValue = "0")
    public Integer line6;

    /**
     * ７行目の内容
     */
    public final static String LINE7 = "line7";
    @DatabaseField(columnName = LINE7, canBeNull = false, defaultValue = "0")
    public Integer line7;

    /**
     * ８行目の内容
     */
    public final static String LINE8 = "line8";
    @DatabaseField(columnName = LINE8, canBeNull = false, defaultValue = "0")
    public Integer line8;

    /**
     * ９行目の内容
     */
    public final static String LINE9 = "line9";
    @DatabaseField(columnName = LINE9, canBeNull = false, defaultValue = "0")
    public Integer line9;

    /**
     * １０行目の内容
     */
    public final static String LINE10 = "line10";
    @DatabaseField(columnName = LINE10, canBeNull = false, defaultValue = "0")
    public Integer line10;
}
