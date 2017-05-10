package com.onekeyask.lawyer.ui.act;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseActivity;
import com.onekeyask.lawyer.global.BaseEvent;
import com.onekeyask.lawyer.ui.fragment.HomeFoundFragment;
import com.onekeyask.lawyer.ui.fragment.HomeIndexFragment;
import com.onekeyask.lawyer.ui.fragment.HomeInfoFragment;
import com.onekeyask.lawyer.ui.fragment.HomeServiceFragment;

public class MainActivity extends BaseActivity {

    private RelativeLayout[] mTabs;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottom();
    }

    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);

        if (event.getCode() == BaseEvent.GO_SERVICE){
            index = 1;
            goTag();
        }
    }

    private void initBottom() {
        HomeIndexFragment indexFragment = new HomeIndexFragment();
        HomeServiceFragment serviceFragment = new HomeServiceFragment();
        HomeFoundFragment foundFragment = new HomeFoundFragment();
        HomeInfoFragment infoFragment = new HomeInfoFragment();

        mTabs = new RelativeLayout[4];
        mTabs[0] = (RelativeLayout) findViewById(R.id.rl_1);
        mTabs[1] = (RelativeLayout) findViewById(R.id.rl_2);
        mTabs[2] = (RelativeLayout) findViewById(R.id.rl_3);
        mTabs[3] = (RelativeLayout) findViewById(R.id.rl_4);
        // select first tab
        mTabs[0].setSelected(true);

        fragments = new Fragment[]{indexFragment, serviceFragment, foundFragment, infoFragment};

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_change, indexFragment)
                .add(R.id.fl_change, serviceFragment).hide(serviceFragment)
                .add(R.id.fl_change, foundFragment).hide(foundFragment)
                .add(R.id.fl_change, infoFragment).hide(infoFragment)
                .show(indexFragment)
                .commit();
    }

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_1:
                index = 0;
                break;
            case R.id.rl_2:
                index = 1;
                break;
            case R.id.rl_3:
                index = 2;
                break;
            case R.id.rl_4:
                index = 3;
                break;
        }
        goTag();
    }

    private void goTag() {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fl_change, fragments[index]);
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }
        mTabs[currentTabIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);

        setBottomBg(index);

        currentTabIndex = index;
    }

    public void setBottomBg(int index) {
        TextView textView1 = (TextView) findViewById(R.id.tv_1);
        TextView textView2 = (TextView) findViewById(R.id.tv_2);
        TextView textView3 = (TextView) findViewById(R.id.tv_3);
        TextView textView4 = (TextView) findViewById(R.id.tv_4);
        ImageView imageView1 = (ImageView) findViewById(R.id.btn_1);
        ImageView imageView2 = (ImageView) findViewById(R.id.btn_2);
        ImageView imageView3 = (ImageView) findViewById(R.id.btn_3);
        ImageView imageView4 = (ImageView) findViewById(R.id.btn_4);
        textView1.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.black));
        textView2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.black));
        textView3.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.black));
        textView4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.black));
        imageView1.setImageResource(R.drawable.bottom_01);
        imageView2.setImageResource(R.drawable.bottom_02);
        imageView3.setImageResource(R.drawable.bottom_03);
        imageView4.setImageResource(R.drawable.bottom_04);

        switch (index) {
            case 0:
                textView1.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
                imageView1.setImageResource(R.drawable.bottom_011);
                break;
            case 1:
                textView2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
                imageView2.setImageResource(R.drawable.bottom_022);
                break;
            case 2:
                textView3.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
                imageView3.setImageResource(R.drawable.bottom_033);
                break;
            case 3:
                textView4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
                imageView4.setImageResource(R.drawable.bottom_044);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showShort("再按一次返回键回到桌面");
                exitTime = System.currentTimeMillis();
            } else {
                cancelToast();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
