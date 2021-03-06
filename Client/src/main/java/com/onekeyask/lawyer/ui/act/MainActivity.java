package com.onekeyask.lawyer.ui.act;

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
import android.os.Environment;
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
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.CheckUpDate;
import com.onekeyask.lawyer.entity.GetRed;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseActivity;
import com.onekeyask.lawyer.global.BaseEvent;
import com.onekeyask.lawyer.global.DownLoadAPK;
import com.onekeyask.lawyer.ui.act.user.LoginActivity;
import com.onekeyask.lawyer.ui.fragment.HomeFoundFragment;
import com.onekeyask.lawyer.ui.fragment.HomeIndexFragment;
import com.onekeyask.lawyer.ui.fragment.HomeInfoFragment;
import com.onekeyask.lawyer.ui.fragment.HomeServiceFragment;
import com.onekeyask.lawyer.utils.UserService;

import java.io.File;

public class MainActivity extends BaseActivity {

    private RelativeLayout[] mTabs;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;
    private long exitTime = 0;
    private TextView tv_red;
    private DownloadManager downloadManager;
    private boolean isWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_red = (TextView) findViewById(R.id.tv_red);
        initBottom();
        getRed();
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        UpdateInfo();
    }

    private void UpdateInfo() {

        isWifi = NetworkInfo.State.CONNECTED == ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        try {
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            final PackageInfo packInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

            OkGo.<String>get(Apis.Checkupdate).params("app_id", packInfo.versionName)
                    .params("plat", "Android").execute(new StringCallback() {
                @Override
                public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                    CheckUpDate date = (new Gson()).fromJson(response.body(), CheckUpDate.class);
                    if (date.getCode() == 0) {
                        if (date.getData() == null){
                            return;
                        }
                        String vName = date.getData().getVersion();
                        if (!vName.equals(packInfo.versionName)) {
                            String downloadUrl = date.getData().getUpdateUrl();

                            if (isWifi) {
                                //wifi下自动下载最新版本，检测目录下是否已经下载好
                                String SDPATH = Environment.getExternalStorageDirectory().getPath() + "/lawyer/芝麻律师" + vName + ".apk";
                                if (new File(SDPATH).exists()) {
                                    //安装SDPATH的文件
                                    InatallDialog(SDPATH);
                                } else {
                                    showShort("正在后台下载，请稍后...");
                                    DownLoadAPK.downloadAPK(downloadManager, downloadUrl, "芝麻律师" + vName, "");
                                }
                            } else {
                                myDialog(downloadManager, downloadUrl, vName);
                            }
                        }
                    }
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getRed() {
        if (UserService.service(getBaseContext()).getUserId() != 0)
            OkGo.<String>get(Apis.GetRed).params("userId", UserService.service(getBaseContext()).getUserId()).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {

                    GetRed red = (new Gson()).fromJson(response.body(), GetRed.class);
                    BaseEvent.getRed = red;
                    if (red.getCode() == 0) {

                        if (red.getData().getMessageIds().size() != 0) {
                            //消息中心的右上角小红点显示
                        } else {

                        }

                        if ((red.getData().getChatIds().size() + red.getData().getUserServiceInfoIds().size()) != 0) {
                            tv_red.setVisibility(View.VISIBLE);
                        } else {
                            tv_red.setVisibility(View.GONE);
                        }
                    }
//                else if (red.getCode() == -100){
//                    UserService service = UserService.service(getBaseContext());
//                    service.setUserName("");
//                    service.setToken("-1");
//                    service.setHeadURL("");
//                    service.setUserId(0);
//                    finish();
//                    startActivity(LoginActivity.class);
//                }
                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();

        setRed();
    }

    private void setRed() {
        if (BaseEvent.getRed != null)
            if (BaseEvent.getRed.getCode() == 0) {

                if (BaseEvent.getRed.getData().getMessageIds().size() != 0) {
                    //消息中心的右上角小红点显示
                } else {

                }

                if ((BaseEvent.getRed.getData().getChatIds().size()+BaseEvent.getRed.getData().getUserServiceInfoIds().size()>0)) {
                    tv_red.setVisibility(View.VISIBLE);
                } else {
                    tv_red.setVisibility(View.GONE);
                }
            }
    }

    public void InatallDialog(final String SDPATH) {
        new AlertDialog.Builder(this).setTitle("版本更新")//对话框标题
                .setMessage("已下载完成最新版本，是否现在安装？")//对话框提示正文
//                .setIcon(R.mipmap.ic_launcher)//对话框标题上的图片
                .setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
                    @Override//取消按钮
                    public void onClick(DialogInterface dialog, int which) {
//                        showShort("请尽快更新");
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
        new AlertDialog.Builder(this).setTitle("版本更新")//对话框标题
                .setMessage("检测到当前应用有新版本发布")//对话框提示正文
//                .setIcon(R.mipmap.ic_launcher)//对话框标题上的图片
                .setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
                    @Override//取消按钮
                    public void onClick(DialogInterface dialog, int which) {
//                        showShort("请尽快更新");
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

        if (event.getCode() == BaseEvent.GO_SERVICE) {
            index = 1;
            if (currentTabIndex != 1) {
                if (UserService.service(getBaseContext()).isLogin()) {
                    goServiceFragment();
                } else {
                    startActivity(LoginActivity.class);
                }
            } else {
                goTag();
            }
        } else if (event.getCode() == BaseEvent.GO_DISCOVER) {
            index = 2;
            if (currentTabIndex != 2) {
                goFoundFragment();
            } else {
                goTag();
            }
        } else if (event.getCode() == BaseEvent.NOTIFICATION_MSG) {
            if (UserService.service(getBaseContext()).getUserId() != 0)
                OkGo.<String>get(Apis.GetRed).params("userId", UserService.service(getBaseContext()).getUserId()).execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        GetRed red = (new Gson()).fromJson(response.body(), GetRed.class);
                        BaseEvent.getRed.getData().getUserServiceInfoIds().addAll(red.getData().getUserServiceInfoIds());
                        BaseEvent.getRed.getData().getChatIds().addAll(red.getData().getChatIds());
                        setRed();
                    }
                });
        }
    }

    HomeIndexFragment indexFragment;
    HomeServiceFragment serviceFragment;
    HomeFoundFragment foundFragment;
    HomeInfoFragment infoFragment;

    private void initBottom() {
        indexFragment = new HomeIndexFragment();
        serviceFragment = new HomeServiceFragment();
        foundFragment = new HomeFoundFragment();
        infoFragment = new HomeInfoFragment();

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
//                .add(R.id.fl_change, serviceFragment).hide(serviceFragment)
//                .add(R.id.fl_change, foundFragment).hide(foundFragment)
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
        if (index == 1 && currentTabIndex != 1) {
            if (UserService.service(getBaseContext()).isLogin()) {
                goServiceFragment();
            } else {
                startActivity(LoginActivity.class);
            }
        } else if (index == 2 && currentTabIndex != 2) {
            goFoundFragment();
        } else {
            goTag();
        }
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

    private void goServiceFragment() {
        if (serviceFragment == null) {
            serviceFragment = new HomeServiceFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fl_change, serviceFragment).hide(serviceFragment).commit();
        }
        goTag();
    }

    private void goFoundFragment() {
        if (foundFragment == null) {
            foundFragment = new HomeFoundFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fl_change, foundFragment).hide(foundFragment).commit();
        }
        goTag();
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
