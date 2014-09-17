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
package com.yojiokisoft.ejalice;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.yojiokisoft.ejalice.dao.SettingDao;
import com.yojiokisoft.ejalice.util.MyResource;

/**
 * メインアクティビティ
 */
public class MainActivity extends Activity {
    private AdView adView;
    private ViewPager mPager;
    private TextView mHelpText;
    private TextView mDelTransButton;
    private TextView mAddTransButton;

    /**
     * AdMobの初期化
     */
    private void initAdMob() {
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.admob_id));
        adView.setAdSize(AdSize.BANNER);

        LinearLayout layout_ad = (LinearLayout) findViewById(R.id.ad_wrapper);
        layout_ad.addView(adView);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)       // エミュレータ
                .addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4") // Galaxy Nexus テスト用携帯電話
                .build();
        adView.loadAd(adRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        MyResource.setTextSizeByInch(this, (ViewGroup) findViewById(R.id.main_wrapper));

        MyPagerAdapter adapter = new MyPagerAdapter(this);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(adapter);

        mHelpText = (TextView) findViewById(R.id.help_text);
        mHelpText.setOnClickListener(mHelpTextClicked);
        mDelTransButton = (TextView) findViewById(R.id.del_trans);
        mDelTransButton.setOnClickListener(mDelTransButtonClicked);
        mAddTransButton = (TextView) findViewById(R.id.add_trans);
        mAddTransButton.setOnClickListener(mAddTransButtonClicked);

        toggleVisibility(mHelpText);
        toggleVisibility(mDelTransButton);
        toggleVisibility(mAddTransButton);

        // setup adMob
        initAdMob();
    }

    @Override
    protected void onPause() {
        int page = mPager.getCurrentItem();
        SettingDao.getInstance().setCurrentPage(page);
        adView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int page = SettingDao.getInstance().getCurrentPage();
        mPager.setCurrentItem(page);
        adView.resume();
    }

    @Override
    protected void onDestroy() {
        adView.destroy();
        super.onDestroy();
    }

    /**
     * 訳消すボタンのクリック
     */
    private View.OnClickListener mDelTransButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mHelpText.getVisibility() == View.VISIBLE) {
                mHelpTextClicked.onClick(view);
                return;
            }
            ((MyPagerAdapter) mPager.getAdapter()).delTrans();
        }
    };

    /**
     * 訳出すボタンのクリック
     */
    private View.OnClickListener mAddTransButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mHelpText.getVisibility() == View.VISIBLE) {
                mHelpTextClicked.onClick(view);
                return;
            }
            ((MyPagerAdapter) mPager.getAdapter()).addTrans();
        }
    };

    /**
     * テキストビューの透明／非透明の切り替え
     *
     * @param view テキストビュー
     */
    private void toggleVisibility(TextView view) {
        if ("1".equals(view.getTag())) {
            view.setTag("0");
            view.setTextColor(Color.TRANSPARENT);
            view.setBackgroundDrawable(null);
        } else {
            view.setTag("1");
            view.setTextColor(Color.WHITE);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.help_button));
        }
    }

    /**
     * ヘルプテキストのクリック
     */
    private View.OnClickListener mHelpTextClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            toggleVisibility(mHelpText);
            toggleVisibility(mDelTransButton);
            toggleVisibility(mAddTransButton);

            mHelpText.setVisibility(View.INVISIBLE);
        }
    };
}
