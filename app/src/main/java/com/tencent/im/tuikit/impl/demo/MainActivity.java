package com.tencent.im.tuikit.impl.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.tencent.qcloud.tuikit.tuicontact.ui.pages.TUIContactFragment;
import com.tencent.qcloud.tuikit.tuiconversation.ui.page.TUIConversationFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        imTuikitInit();
    }

    //初始化IM组件
    private void imTuikitInit() {
        List<Fragment> fragments = new ArrayList<>();
        // tuiconversation 组件提供的会话界面
        fragments.add(new TUIConversationFragment());
        // tuicontact 组件提供的联系人界面
        fragments.add(new TUIContactFragment());
        FragmentAdapter fragmentAdapter = new FragmentAdapter(this);
        fragmentAdapter.setFragmentList(fragments);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0, false);
    }

}