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
import com.yojiokisoft.ejalice.entity.PagesEntity;

import java.sql.SQLException;

/**
 * ページのDAO
 */
public class PagesDao {
    private static PagesDao mInstance = null;
    private static Dao<PagesEntity, Integer> mDao = null;

    /**
     * インスタンスの取得.
     *
     * @return DatabaseHelper
     */
    public static PagesDao getInstance() {
        if (mInstance == null) {
            mInstance = new PagesDao();
            try {
                mDao = DatabaseHelper.getInstance().getDao(
                        PagesEntity.class);
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
    public int create(PagesEntity entity) {
        int ret = -1;
        try {
            ret = mDao.create(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 指定ページのデータを取得する
     *
     * @param pageNo ページNo
     * @return データ(nullの場合はエラー)
     */
    public PagesEntity getData(int pageNo) {
        PagesEntity entity = null;
        try {
            entity = mDao.queryForId(pageNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }
}
