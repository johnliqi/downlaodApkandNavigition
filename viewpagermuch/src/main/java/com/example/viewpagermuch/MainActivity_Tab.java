package com.example.viewpagermuch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.example.viewpagermuch.fragment.TabFragment;
import com.example.viewpagermuch.view.Tabview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity_Tab extends AppCompatActivity {
    ViewPager mVpMain;
    private List<String> mTitles = new ArrayList<String>(Arrays.asList("微信", "通讯", "发现", "我的"));
    Tabview mBtnWeChat;
    Tabview mBtnFriend;
    Tabview mBtnFind;
    Tabview mBtnMine;
    SparseArray<TabFragment> mFragments = new SparseArray<>();
    List<Tabview> mtabs = new ArrayList<>();
    private static final String BUNDLE_KEY_POS="bundle_key_position";
private int mCurTabPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        if (savedInstanceState!=null){
            mCurTabPos=savedInstanceState.getInt(BUNDLE_KEY_POS,0);
        }
        initView();

        initPagerAdapter();
        initEvents();
    }

    public void changeTab() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_KEY_POS,mVpMain.getCurrentItem());
        super.onSaveInstanceState(outState);


    }

    private void initPagerAdapter() {
        mVpMain.setOffscreenPageLimit(mTitles.size());
        mVpMain.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                TabFragment tabFragment = TabFragment.newInstance(mTitles.get(i));
                if (i == 0) { //从fragment 向activity 传递参数
                    tabFragment.setListener(new TabFragment.OnTitleClickListener() {
                        @Override
                        public void onClickListener(String Title) {
                            changeTab();
                        }
                    });
                }
                return
                        tabFragment;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                TabFragment fragment = (TabFragment) super.instantiateItem(container, position);
                mFragments.put(position, fragment);
                return super.instantiateItem(container, position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                mFragments.remove(position);
                super.destroyItem(container, position, object);
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }

        });
        mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int progress) {
                if (positionOffset > 0) {
                    Tabview left = mtabs.get(position);
                    Tabview right = mtabs.get(position + 1);
                    left.setProgress((1 - positionOffset));
                    right.setProgress((positionOffset));
                }

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void initEvents() {
        for (int i = 0; i < mtabs.size(); i++) {
            Tabview tabview = mtabs.get(i);
            final int finalI = i;
            tabview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mVpMain.setCurrentItem(finalI, false);
                    setCurrentTab(finalI);
                }
            });
        }
    }

    public void initView() {
        mVpMain = findViewById(R.id.vp_main);
        mBtnWeChat = findViewById(R.id.btn_we_chat);
        mBtnFriend = findViewById(R.id.btn_friend);
        mBtnFind = findViewById(R.id.btn_find);
        mBtnMine = findViewById(R.id.btn_mine);
        mBtnWeChat.setIconAndText(R.drawable.wechat_normal, R.drawable.wechat_selected, "微信");
        mBtnFriend.setIconAndText(R.drawable.friend_normal, R.drawable.friend_selected, "通讯录");
        mBtnFind.setIconAndText(R.drawable.find_normal, R.drawable.find_selected, "发现");
        mBtnMine.setIconAndText(R.drawable.mine_normal, R.drawable.mine_selectd, "我的");
        mtabs.add(mBtnWeChat);
        mtabs.add(mBtnFriend);
        mtabs.add(mBtnFind);
        mtabs.add(mBtnMine);
        setCurrentTab(mCurTabPos);
        mBtnWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabFragment tabFragment = mFragments.get(0);
                if (tabFragment != null) {
                    tabFragment.changeTitle("微信 change" + "");
                }
            }
        });


    }

    private void setCurrentTab(int position) {
        for (int i = 0; i < mtabs.size(); i++) {
            Tabview tabview = mtabs.get(i);
            if (i == 0) {
                tabview.setProgress(1);
            } else {
                tabview.setProgress(0);
            }
        }
    }
}
