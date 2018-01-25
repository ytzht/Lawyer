package com.onekeyask.lawfirm.ui.act;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.GetRed;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseActivity;
import com.onekeyask.lawfirm.global.BaseEvent;
import com.onekeyask.lawfirm.global.DownLoadAPK;
import com.onekeyask.lawfirm.ui.fragment.HomeFoundFragment;
import com.onekeyask.lawfirm.ui.fragment.HomeIndexFragment;
import com.onekeyask.lawfirm.ui.fragment.HomeInfoFragment;
import com.onekeyask.lawfirm.ui.fragment.HomeServiceFragment;
import com.onekeyask.lawfirm.utils.UserService;


public class MainActivity extends BaseActivity {
    private RelativeLayout[] mTabs;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;
    private TextView tv_red;
    private DownloadManager downloadManager;
    private boolean isWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_red = (TextView) findViewById(R.id.tv_red);
        initBottom();
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//        UpdateInfo();
    }

    private void UpdateInfo() {

        isWifi = NetworkInfo.State.CONNECTED == ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        try {
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

            OkGo.<String>get(Apis.Checkupdate).params("app_id", packInfo.versionName)
                    .params("plat", "Android").execute(new StringCallback() {
                @Override
                public void onSuccess(com.lzy.okgo.model.Response<String> response) {
//                    if (isWifi) {
//                        //wifi下自动下载最新版本，检测目录下是否已经下载好
//                        String SDPATH = Environment.getExternalStorageDirectory().getPath() + "/lawyer/lawyer" + vName + ".apk";
//                        if (new File(SDPATH).exists()) {
//                            //安装SDPATH的文件
//                            InatallDialog(SDPATH);
//                        } else {
//                            showShort("正在后台下载，请稍后...");
//                            DownLoadAPK.downloadAPK(downloadManager, downloadUrl, "芝麻律师" + vName, "");
//                        }
//                    } else {
//                        myDialog(downloadManager, downloadUrl, vName);
//                    }
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        OkGo.<String>get(Apis.GetRed).params("userId", UserService.service(getBaseContext()).getLawyerId()).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                GetRed red = (new Gson()).fromJson(response.body(), GetRed.class);
                if (red.getCode() == 0){

                    if (red.getData().getMessageIds().size() != 0){
                        //消息中心的右上角小红点显示
                    }

                    if ((red.getData().getChatIds().size() + red.getData().getUserServiceInfoIds().size()) != 0){
                        tv_red.setVisibility(View.VISIBLE);
                    }else {
                        tv_red.setVisibility(View.GONE);
                    }
                }else {
//                    showShort(red.getMsg());
                }
            }
        });
    }

    public void InatallDialog(final String SDPATH) {
        new AlertDialog.Builder(this).setTitle("新版本提醒")//对话框标题
                .setMessage("已下载完成最新版本，是否现在安装？")//对话框提示正文
                .setIcon(R.mipmap.ic_launcher)//对话框标题上的图片
                .setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
                    @Override//取消按钮
                    public void onClick(DialogInterface dialog, int which) {
                        showShort("请尽快更新");
                    }
                }).setPositiveButton("立即安装", new DialogInterface.OnClickListener() {
            @Override//确定按钮
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + SDPATH), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }).setCancelable(false)//点击其他区域关闭对话框
                .show();
    }

    public void myDialog(final DownloadManager downloadManager, final String url, final String vName) {
        new AlertDialog.Builder(this).setTitle("益善通新版本提醒")//对话框标题
                .setMessage("本期做了一些优化体验，BUG修复，快来试试吧？")//对话框提示正文
                .setIcon(R.mipmap.ic_launcher)//对话框标题上的图片
                .setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
                    @Override//取消按钮
                    public void onClick(DialogInterface dialog, int which) {
                        showShort("请尽快更新");
                    }
                }).setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
            @Override//确定按钮
            public void onClick(DialogInterface dialog, int which) {
                showShort("正在后台下载，请稍后...");
                DownLoadAPK.downloadAPK(downloadManager, url, "芝麻律师" + vName, "");
            }
        }).setCancelable(false)//点击其他区域关闭对话框
                .show();
    }

    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);

        if (event.getCode() == BaseEvent.GO_SERVICE){
            index = 1;
            goTag();
        } else if (event.getCode() == BaseEvent.FINISH_MAIN){
            finish();
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
        TextView textView1 = (TextView) findViewById(R.id.tv_11);
        TextView textView2 = (TextView) findViewById(R.id.tv_22);
        TextView textView3 = (TextView) findViewById(R.id.tv_33);
        TextView textView4 = (TextView) findViewById(R.id.tv_44);
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


    private long exitTime = 0;

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
