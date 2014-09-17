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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.yojiokisoft.ejalice.App;
import com.yojiokisoft.ejalice.util.MyConst;

/**
 * 設定情報のDAO
 */
public class SettingDao {
    private static SettingDao mInstance = null;
    private static SharedPreferences mSharedPref = null;
    private static Context mContext;

    /**
     * コンストラクタは公開しない
     * インスタンスを取得する場合は、getInstanceを使用する.
     */
    private SettingDao() {
    }

    /**
     * インスタンスの取得.
     *
     * @return SettingDao
     */
    public static SettingDao getInstance() {
        if (mInstance == null) {
            mInstance = new SettingDao();
            mContext = App.getInstance().getAppContext();
            mSharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        }
        return mInstance;
    }

    /**
     * @return カレントページ
     */
    public int getCurrentPage() {
        return mSharedPref.getInt(MyConst.PK_CURRENT_PAGE, 0);
    }

    /**
     * カレントページのセット
     * @param page ページ
     */
    public void setCurrentPage(int page) {
        mSharedPref.edit().putInt(MyConst.PK_CURRENT_PAGE, page).commit();
    }
}
