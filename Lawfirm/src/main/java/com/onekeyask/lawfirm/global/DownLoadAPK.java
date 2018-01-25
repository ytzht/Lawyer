package com.onekeyask.lawfirm.global;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.Build;

/**
 * Created by Administrator on 2016/8/13.am
 */

public class DownLoadAPK {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static long downloadAPK(DownloadManager downloadManager, String apkUrl, String name, String desc){

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        request.setDestinationInExternalPublicDir("lawyer", name+".apk");//表示设置下载地址为sd卡的volunteer文件夹，文件名为name.apk。
        request.setTitle(name);//设置下载中通知栏提示的标题
        request.setDescription(desc);//设置下载中通知栏提示的介绍
        request.setVisibleInDownloadsUi(true);  //设置显示下载界面
        request.setMimeType("application/vnd.android.package-archive");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);//表示下载进行中和下载完成的通知栏是否显示。
        // 默认只显示下载中通知。
        // VISIBILITY_VISIBLE_NOTIFY_COMPLETED表示下载完成后显示通知栏提示。VISIBILITY_HIDDEN表示不显示任何通知栏提示，
        // 这个需要在AndroidMainfest中添加权限android.permission.DOWNLOAD_WITHOUT_NOTIFICATION.

//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);//表示下载允许的网络类型，默认在任何网络下都允许下载。
        //有NETWORK_MOBILE、NETWORK_WIFI、NETWORK_BLUETOOTH三种及其组合可供选择。
        //如果只允许wifi下载，而当前网络为3g，则下载会等待。

//        request.setAllowedOverRoaming(true);//移动网络情况下是否允许漫游。

//        request.setMimeType("application/cn.trinea.download.file");//设置下载文件的mineType。
        // 因为下载管理Ui中点击某个已下载完成文件及下载完成点击通知栏提示都会根据mimeType去打开文件，所以我们可以利用这个属性。
        // 比如上面设置了mimeType为application/cn.trinea.download.file，
        // 我们可以同时设置某个Activity的intent-filter为application/cn.trinea.download.file，用于响应点击的打开文件。

//        request.allowScanningByMediaScanner();//表示允许MediaScanner扫描到这个文件，默认不允许。

        //request.addRequestHeader(String header, String value)
        //添加请求下载的网络链接的http头，比如User-Agent，gzip压缩等

        return downloadManager.enqueue(request);
    }
}
