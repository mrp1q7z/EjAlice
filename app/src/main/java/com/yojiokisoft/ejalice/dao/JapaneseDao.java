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
package com.yojiokisoft.ejalice.dao;

import com.j256.ormlite.dao.Dao;
import com.yojiokisoft.ejalice.entity.JapaneseEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * 日本語のDAO
 */
public class JapaneseDao {
    private static JapaneseDao mInstance = null;
    private static Dao<JapaneseEntity, Integer> mDao = null;

    /**
     * インスタンスの取得.
     *
     * @return DatabaseHelper
     */
    public static JapaneseDao getInstance() {
        if (mInstance == null) {
            mInstance = new JapaneseDao();
            try {
                mDao = DatabaseHelper.getInstance().getDao(
                        JapaneseEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mInstance;
    }

    /**
     * @return 件数
     */
    public int getCount() {
        try {
            return (int) mDao.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 新規に行を作成する
     *
     * @param entity
     * @return 1=正常終了,else=エラー
     */
    public int create(JapaneseEntity entity) {
        int ret = -1;
        try {
            ret = mDao.create(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 指定IDから指定件数分のデータを取得する
     *
     * @param startId 開始ID
     * @param count   取得する件数
     * @return データリスト
     */
    public List<JapaneseEntity> getBetween(int startId, int count) {
        List<JapaneseEntity> list = null;
        try {
            list = mDao.queryBuilder().limit((long) count).where().ge(JapaneseEntity.ID, startId).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
