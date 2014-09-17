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

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yojiokisoft.ejalice.dao.EnglishDao;
import com.yojiokisoft.ejalice.dao.JapaneseDao;
import com.yojiokisoft.ejalice.dao.PagesDao;
import com.yojiokisoft.ejalice.entity.EnglishEntity;
import com.yojiokisoft.ejalice.entity.JapaneseEntity;
import com.yojiokisoft.ejalice.entity.PagesEntity;
import com.yojiokisoft.ejalice.util.MyResource;

import java.util.List;

/**
 * Bookのページアダプター
 * Created by taoka on 14/09/13.
 */
public class MyPagerAdapter extends PagerAdapter {
    private static final int PAGE_MAX = 156; // 最大ページ数
    private static final int LINE_MAX = 8; // 最大行数

    // Dao
    private PagesDao mPagesDao;
    private EnglishDao mEnglishDao;
    private JapaneseDao mJapaneseDao;

    private ViewWrapper mView;

    private Context mContext;
    private LayoutInflater mInflater;
    private int mTransCursor; // 日本語訳のカーソル

    /**
     * コンストラクタ
     *
     * @param context コンテキスト
     */
    public MyPagerAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mPagesDao = PagesDao.getInstance();
        mEnglishDao = EnglishDao.getInstance();
        mJapaneseDao = JapaneseDao.getInstance();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        FrameLayout layout = (FrameLayout) mInflater.inflate(R.layout.page_book, null);
        MyResource.setTextSizeByInch(mContext, layout);
        printPage(layout, position + 1);
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return PAGE_MAX;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (FrameLayout) object;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mView = new ViewWrapper((FrameLayout) object);

        mTransCursor = 0;
        for (int i = 0; i < LINE_MAX; i++) {
            if (mView.japaneseText[i].getVisibility() == View.VISIBLE) {
                mTransCursor += 1;
            }
        }
    }

    /**
     * ページの表示
     *
     * @param rootView ルートビュー
     * @param pageNo   ページ番号(1-)
     */
    private void printPage(View rootView, int pageNo) {
        ViewWrapper view = new ViewWrapper(rootView);

        view.pageNo.setText("" + pageNo);
        PagesEntity page = mPagesDao.getData(pageNo);
        if (page == null) {
            return;
        }

        if (page.image.length() == 0) {
            view.image.setImageDrawable(null);
        } else {
            int resId = MyResource.getResourceIdByName(page.image);
            view.image.setImageDrawable(mContext.getResources().getDrawable(resId));
        }
        int lineCount = getPageLineCount(page);
        printEnglish(view, page.line1, lineCount);
        printJapanese(view, page.line1, lineCount);
    }

    /**
     * ページ内になる行数を数える
     *
     * @param page ページエンティティ
     * @return 行数
     */
    private int getPageLineCount(PagesEntity page) {
        int count = 0;
        count += (page.line1 != 0) ? 1 : 0;
        count += (page.line2 != 0) ? 1 : 0;
        count += (page.line3 != 0) ? 1 : 0;
        count += (page.line4 != 0) ? 1 : 0;
        count += (page.line5 != 0) ? 1 : 0;
        count += (page.line6 != 0) ? 1 : 0;
        count += (page.line7 != 0) ? 1 : 0;
        count += (page.line8 != 0) ? 1 : 0;
        count += (page.line9 != 0) ? 1 : 0;
        count += (page.line10 != 0) ? 1 : 0;
        return count;
    }

    /**
     * 英語の表示
     *
     * @param view      ビューラッパー
     * @param startId   開始行番号
     * @param lineCount 表示行数
     */
    private void printEnglish(ViewWrapper view, int startId, int lineCount) {
        int i;
        for (i = 0; i < LINE_MAX; i++) {
            if (i < lineCount) {
                view.englishText[i].setVisibility(View.VISIBLE);
            } else {
                view.englishText[i].setVisibility(View.GONE);
            }
        }
        if (lineCount <= 0) {
            return;
        }

        List<EnglishEntity> list = mEnglishDao.getBetween(startId, lineCount);
        if (list == null) {
            return;
        }
        int max = list.size();
        EnglishEntity english;
        for (i = 0; i < max; i++) {
            english = list.get(i);
            if (view.englishText.length <= i) {
                break;
            }
            view.englishText[i].setText(english.english);
        }
    }

    /**
     * 日本語の表示
     *
     * @param view      ビューラッパー
     * @param startId   開始行番号
     * @param lineCount 表示行数
     */
    private void printJapanese(ViewWrapper view, int startId, int lineCount) {
        mTransCursor = 0;

        int i;
        for (i = 0; i < LINE_MAX; i++) {
            if (i < lineCount) {
                view.japaneseText[i].setVisibility(View.INVISIBLE);
            } else {
                view.japaneseText[i].setVisibility(View.GONE);
            }
        }
        if (lineCount <= 0) {
            return;
        }

        List<JapaneseEntity> list = mJapaneseDao.getBetween(startId, lineCount);
        if (list == null) {
            return;
        }
        int max = list.size();
        JapaneseEntity japanese;
        for (i = 0; i < max; i++) {
            japanese = list.get(i);
            if (view.japaneseText.length <= i) {
                break;
            }
            view.japaneseText[i].setText(japanese.japanese);
        }
    }

    /**
     * 訳消す
     */
    public void delTrans() {
        if (mTransCursor <= 0) {
            return;
        }
        mTransCursor -= 1;
        mView.japaneseText[mTransCursor].setVisibility(View.INVISIBLE);
    }

    /**
     * 訳出す
     */
    public void addTrans() {
        if (mView.japaneseText.length <= mTransCursor) {
            return;
        }
        if (mView.japaneseText[mTransCursor].getVisibility() == View.GONE) {
            return;
        }
        mView.japaneseText[mTransCursor].setVisibility(View.VISIBLE);
        mTransCursor += 1;
    }

    /**
     * ビューラッパー
     */
    private class ViewWrapper {
        public final ImageView image;
        public final TextView[] englishText = new TextView[LINE_MAX];
        public final TextView[] japaneseText = new TextView[LINE_MAX];
        public final TextView pageNo;

        ViewWrapper(View view) {
            pageNo = (TextView) view.findViewById(R.id.page_no);
            image = (ImageView) view.findViewById(R.id.image);

            int i;
            int resId;
            for (i = 0; i < LINE_MAX; i++) {
                resId = MyResource.getResourceIdByName("english_text" + (i + 1), "id");
                englishText[i] = (TextView) view.findViewById(resId);
                resId = MyResource.getResourceIdByName("japanese_text" + (i + 1), "id");
                japaneseText[i] = (TextView) view.findViewById(resId);
            }
        }
    }
}
