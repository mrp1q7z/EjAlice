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
package com.yojiokisoft.ejalice.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yojiokisoft.ejalice.App;
import com.yojiokisoft.ejalice.R;

import java.lang.reflect.Field;

/**
 * リソース関連のユーティリティ
 */
public class MyResource {
    /**
     * リソース名からリソースIDを得る.
     *
     * @param name リソース名
     * @return リソースID
     */
    public static int getResourceIdByName(String name) {
        return getResourceIdByName(name, "drawable");
    }

    /**
     * リソース名からリソースIDを得る.
     *
     * @param name リソース名
     * @param type リソースタイプ
     * @return リソースID
     */
    public static int getResourceIdByName(String name, String type) {
        int resId = 0;
        try {
            Class res;
            if (type.equals("drawable")) {
                res = R.drawable.class;
            } else {
                res = R.id.class;
            }
            Field field = res.getField(name);
            resId = field.getInt(null);
        }
        catch (Exception e) {
            Log.e("MyTag", "Failure to get drawable id.", e);
        }
        return resId;
//        App app = App.getInstance();
//        String packageName = app.getPackageName();
//        return app.getResources().getIdentifier(name, type, packageName);
    }

    /**
     * @return パッケージ情報
     */
    public static PackageInfo getPackageInfo() {
        App app = App.getInstance();
        PackageInfo packageInfo = null;
        try {
            packageInfo = app.getPackageManager()
                    .getPackageInfo(app.getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            MyLog.writeStackTrace(MyConst.BUG_CAUGHT_FILE, e);
        }
        return packageInfo;
    }

    /**
     * dpiをpxに変換
     *
     * @param dpi
     * @return px
     */
    public static int dpi2Px(int dpi) {
        float density = App.getInstance().getResources().getDisplayMetrics().density;
        int px = (int) (dpi * density + 0.5f);
        return px;
    }

    /**
     * 画面の幅と高さを取得する.
     *
     * @param activity
     * @return 画面の幅(=first)と高さ(=second)
     */
    public static Pair<Integer, Integer> getScreenWidthAndHeight(Activity activity) {
        // 画面サイズを取得する
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = (Integer) metrics.widthPixels;
        int screenHeight = (Integer) metrics.heightPixels;
        Pair<Integer, Integer> size = new Pair<Integer, Integer>(screenWidth, screenHeight);
        return size;
    }

    /**
     * @return ステータスバーの高さ
     */
    public static int getStatusBarHeight() {
        return dpi2Px(25);
    }

    /**
     * 文字サイズをレイアウトサイズに応じて変更します。
     *
     * @param rootView 大元のレイアウト（ViewGroupであること）
     */
    public static void setTextSizeByInch(Context context, ViewGroup rootView) {
        Activity activity = (Activity) context;
        // ディスプレイ情報を取得する
        final DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // ピクセル数（width, height）を取得する
        final int widthPx = metrics.widthPixels;
        final int heightPx = metrics.heightPixels;
        // dpi (xDpi, yDpi) を取得する
        final float xDpi = metrics.xdpi;
        final float yDpi = metrics.ydpi;
        // インチ（width, height) を計算する
        final float widthIn = widthPx / xDpi;
        final float heightIn = heightPx / yDpi;
        // 画面サイズ（インチ）を計算する
        final double in = Math.sqrt(widthIn * widthIn + heightIn * heightIn);
        // 4インチ以上は比率に応じて文字を拡大
        if (in > 4) {
            // 親のレイアウトを取得
            setTextSizes(rootView, in / 4);
        }
    }

    /**
     * 親のレイアウトに設定されている子Viewの文字を画面サイズに応じて倍加します。
     */
    private static void setTextSizes(ViewGroup parent, double multiple) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (view instanceof ViewGroup) {
                setTextSizes((ViewGroup) view, multiple);
            } else if (view instanceof TextView) {
                TextView targetView = (TextView) view;
                targetView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (targetView.getTextSize() * multiple));
            }
        }
    }
}
