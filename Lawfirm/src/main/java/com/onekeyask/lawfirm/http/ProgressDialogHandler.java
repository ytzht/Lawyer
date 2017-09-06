package com.onekeyask.lawfirm.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by zht on 2017/04/19 17:22
 */

public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private KProgressHUD pd;

    private Context context;
    private boolean show;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                 boolean cancelable,boolean show) {
        super();
        this.context = context;
        this.show = show;
    }

    private void initProgressDialog(){
        if (pd == null) {

            pd = KProgressHUD.create(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(false);

            if (!pd.isShowing()&&show) {
                pd.show();
            }
        }
    }

    private void dismissProgressDialog(){
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

}