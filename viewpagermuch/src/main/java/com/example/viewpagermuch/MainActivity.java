package com.example.viewpagermuch;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.viewpagermuch.fragment.TabFragment;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity { //微信布局
    ViewPager mVpMain;
    private List<String> mTitles = new ArrayList<String>(Arrays.asList("微信", "通讯", "发现", "我的"));
    Button mBtnWeChat;

    Button mBtnFriend;
    Button mBtnFind;
    Button mBtnMine;
    SparseArray<TabFragment> mFragments = new SparseArray<>();
    List<Button> mtabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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
                    Button left = mtabs.get(position);
                    Button right = mtabs.get(position + 1);
                    left.setText((1 - positionOffset + ""));
                    right.setText((positionOffset + ""));
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

    public void changeTab() {

    }

    public void initView() {
        mVpMain = findViewById(R.id.vp_main);
        mBtnWeChat = findViewById(R.id.btn_we_chat);
        mBtnFriend = findViewById(R.id.btn_friend);
        mBtnFind = findViewById(R.id.btn_find);
        mBtnMine = findViewById(R.id.btn_mine);
        mtabs.add(mBtnWeChat);
        mtabs.add(mBtnFriend);
        mtabs.add(mBtnFind);
        mtabs.add(mBtnMine);
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
}
