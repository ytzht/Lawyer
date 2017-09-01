package com.onekeyask.lawyer.ui.act.consulting;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.ConversationList;
import com.onekeyask.lawyer.entity.LawyerBasic;
import com.onekeyask.lawyer.entity.ResultData;
import com.onekeyask.lawyer.entity.SendMsg;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseActivity;
import com.onekeyask.lawyer.global.BaseEvent;
import com.onekeyask.lawyer.global.L;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.ui.act.lawyer.LawyerDetailActivity;
import com.onekeyask.lawyer.utils.DiffCallTalkingBack;
import com.onekeyask.lawyer.utils.HideUtil;
import com.onekeyask.lawyer.utils.UserService;
import com.onekeyask.lawyer.utils.photo.Info;
import com.onekeyask.lawyer.utils.photo.PhotoView;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import me.iwf.photopicker.PhotoPicker;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class TalkingActivity extends BaseActivity {

    private AlertDialog alertDialog;
    private RecyclerView rlv_talking;
    private LinearLayout iv_input_bottom, iv_voice_bottom, iv_photo_bottom, iv_money_bottom, iv_share_bottom, ll_is_detail;
    private TalkingAdapter talkingAdapter;
    private String userServiceId = "0";
    private long firstConversationId, lastConversationId, conversationId;
    private String cid = "0";
    private ConversationList getList;
    private LinearLayout ll_input_send, ll_bottom_menu, ll_eva_comp, ll_lawyer_info, ll_law_detail;
    private RelativeLayout rl_give_money, rl_again;
    private int LEFT = 1;
    private int size = 10;
    private String orderId = "";
    private TextView tv_send_msg, talk_toolbar_title;
    private EditText et_send_msg;
    private Handler mHandler = new Handler();
    private Menu menu;
    private Toolbar talk_toolbar;
    private boolean hasMore = true;
    private HandlerThread mCheckMsgThread;
    private Handler mCheckMsgHandler;
    private boolean isUpdateInfo;
    private String lawyerId = "-1";
    private static final int MSG_UPDATE_INFO = 0x110;
    private List<ConversationList.ConversationListBean> listBean;
    private PopupWindow popupWindow = null;
    private View popupView;
    private String selectMoney = "2.00";
    private boolean isAskDetail = false;//是否为未接单状态，是的话显示问题详情

    private TextView tv_sel_2;
    private TextView tv_sel_4;
    private TextView tv_sel_6;
    private TextView tv_sel_8;
    private TextView tv_cancel_popup;
    private TextView tv_yes_popup;
    private EditText et_money_popup, et_desc_popup;
    private CircleImageView law_img;
    private TextView law_name, law_office, complaint;

    private PhotoView mPhotoView;
    View mParent;
    View mBg;
    Info mInfo;
    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);
    private int userId;

    private SHARE_MEDIA shareMedia;
    private String shareUrl = "http://ytzht.top";
    private String shareTitle = "你的朋友喊你一起来玩";
    private String shareSummary = "shareSummary";

    private void goShare() {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(iv_share_bottom.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        ShareAction action = new ShareAction(TalkingActivity.this).withText("hello")
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        UMWeb web = new UMWeb(shareUrl);
                        web.setTitle(shareTitle);//标题
                        web.setThumb(new UMImage(getBaseContext(), R.mipmap.ic_launcher));  //缩略图
                        web.setDescription(shareSummary);//描述
                        shareMedia = share_media;

                        if (share_media == SHARE_MEDIA.SINA){
                            showShort("敬请期待");
                        }else {
                            new ShareAction(TalkingActivity.this).withMedia(web)
                                    .setCallback(umShareListener).setPlatform(share_media).share();
                        }
                    }
                });

        ShareBoardConfig config = new ShareBoardConfig();
        config.setTitleVisibility(false);
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setCancelButtonVisibility(false);
        config.setIndicatorVisibility(false);
//                config.setShareboardBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.divider));
        action.open(config);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            L.d("onStart 开始分享");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            L.d("onResult 分享成功");
            goShareSuccess();
            View view1 = LayoutInflater.from(TalkingActivity.this).inflate(R.layout.custom_dialog_share, null, false);
            alertDialog = new AlertDialog.Builder(TalkingActivity.this).setView(view1).setCancelable(false).show();

            view1.findViewById(R.id.tv_share_con).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            L.e("onError 分享失败 " + throwable.toString());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            L.d("onCancel 分项取消");
        }
    };

    private void goShareSuccess() {

        String targetPlat;
        if (shareMedia == SHARE_MEDIA.SINA){
            targetPlat = "3";
        }else if (shareMedia == SHARE_MEDIA.WEIXIN){
            targetPlat = "1";
        }else {
            targetPlat = "2";
        }
        OkGo.<String>post(Apis.SaveShare).params("userId", UserService.service(getBaseContext()).getUserId())
                .params("title", shareTitle)
                .params("summary", shareSummary)
                .params("targetPlat", targetPlat)
                .params("url", shareUrl)//“1”-微信朋友 //“2”-微信朋友圈； //“3”-新浪微博
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                        if (data.getCode() != 0){
                            showShort(data.getMsg());
                        }
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talking);

        initView();

        initPhotoView();

        //创建后台线程
        initBackThread();

//        showLawyerAndComplaint();
    }

    private void initBackThread() {
        mCheckMsgThread = new HandlerThread("TalkingHandlerThread" + cid);
        mCheckMsgThread.start();
        mCheckMsgHandler = new Handler(mCheckMsgThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                checkForUpdate();
                if (isUpdateInfo) {
                    mCheckMsgHandler.sendEmptyMessageDelayed(MSG_UPDATE_INFO, 3000);
                }
            }
        };


    }

    private void checkForUpdate() {
        try {
            //模拟耗时

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    initConversation("1");
                }
            });
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        mCheckMsgThread.quit();
    }

    private void initPhotoView() {
        in.setDuration(300);
        out.setDuration(300);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBg.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mParent = findViewById(R.id.parent);
        mBg = findViewById(R.id.bg);
        mPhotoView = (PhotoView) findViewById(R.id.img);
        mPhotoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mPhotoView.enable();
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBg.startAnimation(out);
                mPhotoView.animaTo(mInfo, new Runnable() {
                    @Override
                    public void run() {
                        mParent.setVisibility(View.GONE);
                    }
                });
            }
        });

    }

    private void initView() {
        HideUtil.init(this);
        if (getIntent().hasExtra("lawyerId")){
            lawyerId = getIntent().getStringExtra("lawyerId");
        }
        userId = UserService.service(getBaseContext()).getUserId();
        userServiceId = getIntent().getStringExtra("sid");
        orderId = getIntent().getStringExtra("oid");
        talk_toolbar_title = (TextView) findViewById(R.id.talk_toolbar_title);
        talk_toolbar_title.setText("聊天页面");
        talk_toolbar = (Toolbar) findViewById(R.id.talk_toolbar);
        setSupportActionBar(talk_toolbar);
        cid = getIntent().getStringExtra("cid");
        ll_input_send = (LinearLayout) findViewById(R.id.ll_input_send);
        ll_bottom_menu = (LinearLayout) findViewById(R.id.ll_bottom_menu);
        ll_eva_comp = (LinearLayout) findViewById(R.id.ll_eva_comp);
        ll_lawyer_info = (LinearLayout) findViewById(R.id.ll_lawyer_info);
        rl_give_money = (RelativeLayout) findViewById(R.id.rl_give_money);
        rl_again = (RelativeLayout) findViewById(R.id.rl_again);
        rlv_talking = (RecyclerView) findViewById(R.id.rlv_talking);
        rl_give_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow = getPopwindow(popupView);
            }
        });
        rlv_talking.setLayoutManager(new LinearLayoutManager(this));
        tv_send_msg = (TextView) findViewById(R.id.tv_send_msg);
        et_send_msg = (EditText) findViewById(R.id.et_send_msg);
        iv_input_bottom = (LinearLayout) findViewById(R.id.iv_input_bottom);
        iv_input_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        iv_voice_bottom = (LinearLayout) findViewById(R.id.iv_voice_bottom);
        iv_photo_bottom = (LinearLayout) findViewById(R.id.iv_photo_bottom);
        iv_money_bottom = (LinearLayout) findViewById(R.id.iv_money_bottom);
        iv_share_bottom = (LinearLayout) findViewById(R.id.iv_share_bottom);
        ll_is_detail = (LinearLayout) findViewById(R.id.ll_is_detail);

        iv_share_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShare();

            }
        });


        iv_money_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow = getPopwindow(popupView);
            }
        });

        tv_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_send_msg.getText().toString())) {
                    sendMsg();
                }
            }
        });
        iv_photo_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(true)
                        .start(TalkingActivity.this, PhotoPicker.REQUEST_CODE);
            }
        });

        rlv_talking.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (((LinearLayoutManager) (recyclerView.getLayoutManager())).findFirstVisibleItemPosition() == 0) {
                        if (hasMore) {
                            initConversation("0");
                        }
                    }
                }

            }
        });


        initPop();


    }

    private void initPop() {
        popupView = LayoutInflater.from(this).inflate(R.layout.popup_select_money, null);
        tv_sel_2 = (TextView) popupView.findViewById(R.id.tv_sel_2);
        tv_sel_4 = (TextView) popupView.findViewById(R.id.tv_sel_4);
        tv_sel_6 = (TextView) popupView.findViewById(R.id.tv_sel_6);
        tv_sel_8 = (TextView) popupView.findViewById(R.id.tv_sel_8);
        tv_cancel_popup = (TextView) popupView.findViewById(R.id.tv_cancel_popup);
        tv_yes_popup = (TextView) popupView.findViewById(R.id.tv_yes_popup);
        initPopupClick();


        et_money_popup = (EditText) popupView.findViewById(R.id.et_money_popup);
        et_desc_popup = (EditText) popupView.findViewById(R.id.et_desc_popup);
        et_money_popup.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        et_money_popup.setText("2.00");
        et_money_popup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_money_popup.getText().toString().equals("") && !et_money_popup.getText().toString().equals(".")) {
                    double money = ((Double.parseDouble(et_money_popup.getText().toString())));
                    if (money != 0) {
                        selectMoney = String.valueOf(money);
                    } else {
                        selectMoney = "";
                    }
                }
                tv_sel_2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_6.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_8.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initPopupClick() {

        tv_cancel_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        tv_yes_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectMoney.equals("")) {
                    showShort("请选择金额");
                } else {
                    popupWindow.dismiss();
//                    showShort("选择" + selectMoney + "元并说" + et_desc_popup.getText().toString());

                    Intent intent = new Intent(TalkingActivity.this, PayLawyerActivity.class);
                    intent.putExtra("name", law_name.getText().toString());
                    intent.putExtra("lawyerId", lawyerId);
                    intent.putExtra("type", "2");
                    intent.putExtra("money", Double.parseDouble(selectMoney));
                    intent.putExtra("summary", et_desc_popup.getText().toString());
                    intent.putExtra("userServiceId", userServiceId);
                    startActivity(intent);
                }
            }
        });
        tv_sel_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMoney = "2.00";
                et_money_popup.setText(selectMoney);
                tv_sel_2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
                tv_sel_4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                tv_sel_4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_6.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_8.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
            }
        });
        tv_sel_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMoney = "4.00";
                et_money_popup.setText(selectMoney);
                tv_sel_4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
                tv_sel_2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                tv_sel_2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_6.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_8.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
            }
        });
        tv_sel_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMoney = "6.00";
                et_money_popup.setText(selectMoney);
                tv_sel_6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
                tv_sel_4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_6.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                tv_sel_4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_8.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
            }
        });
        tv_sel_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMoney = "8.00";
                et_money_popup.setText(selectMoney);
                tv_sel_8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
                tv_sel_4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_8.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                tv_sel_4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_6.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
            }
        });
    }

    private void sendMsg() {
        SubscriberOnNextListener<SendMsg> listener = new SubscriberOnNextListener<SendMsg>() {
            @Override
            public void onNext(SendMsg msg) {
                et_send_msg.setText("");
                initConversation("1");

            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getSendMsg(userId,
                cid, et_send_msg.getText().toString(),
                new ProgressSubscriber<SendMsg>(listener, TalkingActivity.this, false));

    }


    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post(BaseEvent.event(BaseEvent.GO_SERVICE));
        isUpdateInfo = true;
        mCheckMsgHandler.sendEmptyMessage(MSG_UPDATE_INFO);

    }

    @Override
    public void onPause() {
        super.onPause();
        //停止查询
        isUpdateInfo = false;
        mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);
    }

    private void initConversation(final String direction) {

        getResultOnNext = new SubscriberOnNextListener<ConversationList>() {
            @Override
            public void onNext(ConversationList o) {
                ll_bottom_menu.setVisibility(View.VISIBLE);
                ll_input_send.setVisibility(View.VISIBLE);
                getList = o;
                if (!getList.getLawyerId().equals("")){
                    lawyerId = getList.getLawyerId();
                }
                rlv_talking.setVisibility(View.VISIBLE);
                cid = String.valueOf(getList.getChatId());
                userServiceId = String.valueOf(getList.getUserServiceId());
                switch (getList.getStatus()) {
                    case "0"://未接单
                        L.d("未接单");
                        isAskDetail = true;
                        ll_is_detail.setVisibility(View.VISIBLE);
                        isUpdateInfo = true;
                        hideMenu();
                        getAskDetail();
                        break;
                    case "1"://进行中
                        L.d("进行中");
                        isUpdateInfo = true;
                        showMenu();
                        break;
                    case "2"://已结束
                        L.d("已结束");
                        isUpdateInfo = false;
                        hideMenu();
                        //出现略表心意、和再次咨询按钮布局，隐藏输入框
                        ll_input_send.setVisibility(View.GONE);
                        ll_bottom_menu.setVisibility(View.GONE);
                        ll_eva_comp.setVisibility(View.VISIBLE);
                        ll_lawyer_info.setVisibility(View.VISIBLE);
                        showLawyerAndComplaint();
                        showShareMenu();
                        break;
                }
                if (!getList.getStatus().equals("0")) {
                    if (getList.isEvaStatus()) {
                        //已评价
                        hideMenu();
                    } else {
                        showMenu();
                    }
                }
                getList.getFromType();//订单类型 1图文咨询 2免费提问

                ConversationList.ConversationListBean bean = new ConversationList.ConversationListBean
                        ((int) conversationId, 1, 2, "12", "12", "content", false, "00:00");

                if (talkingAdapter != null) {

                    boolean b = (getList.getConversationList().size() > 0);//为true就是有新增数据

                    //改变第一个和最后一个对话的id
                    if (b) {//改变上限和下限的id
                        if (direction.equals("1")) {
                            //向下查数据改变最后一个最近发言的id
                            lastConversationId = getList.getConversationList().get(getList.getConversationList().size() - 1).getConversationId();
                        } else {
                            hasMore = getList.isHasMore();
                            firstConversationId = getList.getConversationList().get(0).getConversationId();
                        }
                        L.d("数据有改变了");

                        List<ConversationList.ConversationListBean> newList = new ArrayList<>();


                        if (direction.equals("1")) {
                            newList.addAll(listBean);
                            newList.addAll(getList.getConversationList());
                        } else {
                            newList.add(0, bean);
                            newList.addAll(getList.getConversationList());
                            List<ConversationList.ConversationListBean> newList1 = new ArrayList<>();
                            newList1.addAll(listBean);
                            newList1.remove(0);
                            newList.addAll(newList1);
                        }

                        L.d("listBean.size() " + listBean.size());
                        DiffUtil.DiffResult diffResult =
                                DiffUtil.calculateDiff(new DiffCallTalkingBack(listBean, newList), true);
                        listBean = newList;
                        L.d("listBean.size() " + listBean.size());
                        talkingAdapter.setList(listBean);
                        diffResult.dispatchUpdatesTo(talkingAdapter);

                        if (direction.equals("1")) {
                            rlv_talking.scrollToPosition(talkingAdapter.getItemCount() - 1);
                        }


                    } else {
                        L.d("数据没改变");
                    }
                    L.d("老数据大小 " + listBean.size() + " 新增数据大小 " + getList.getConversationList().size());


                } else {
                    listBean = getList.getConversationList();
                    talkingAdapter = new TalkingAdapter();
                    listBean.add(0, bean);
                    talkingAdapter.setList(listBean);
                    if (listBean.size() > 0) {
                        firstConversationId = listBean.get(1).getConversationId();
                        lastConversationId = listBean.get(listBean.size() - 1).getConversationId();
                    }
                    rlv_talking.scrollToPosition(talkingAdapter.getItemCount() - 1);
                    rlv_talking.setAdapter(talkingAdapter);
                }

                L.d("firstConversationId " + firstConversationId + " lastConversationId " + lastConversationId);

            }

            @Override
            public void onError(int code, String message) {
                if (code == -130) {
                    showShort("-130");
                } else {
                    showShort(message);
                }
            }
        };

        if (direction.equals("1")) {
            conversationId = lastConversationId;//说明是向下查数据
        } else {
            conversationId = firstConversationId;//向上查数据
        }

        L.d("orderId " + orderId);
        retrofitUtil.getConversationList(UserService.service(getBaseContext()).getUserId(), cid, orderId, conversationId, direction, size, new ProgressSubscriber<ConversationList>(getResultOnNext, TalkingActivity.this, false));

    }

    private void showLawyerAndComplaint() {
        ll_law_detail = (LinearLayout) findViewById(R.id.ll_law_detail);
        law_img = (CircleImageView) findViewById(R.id.law_img);
        law_name = (TextView) findViewById(R.id.law_name);
        law_office = (TextView) findViewById(R.id.law_office);
        complaint = (TextView) findViewById(R.id.complaint);
        ll_law_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TalkingActivity.this, LawyerDetailActivity.class);
                intent.putExtra("lawyerId", Integer.parseInt(lawyerId));
                startActivity(intent);
            }
        });

        SubscriberOnNextListener<LawyerBasic> listener = new SubscriberOnNextListener<LawyerBasic>() {
            @Override
            public void onNext(LawyerBasic lawyerBasic) {
                law_name.setText(lawyerBasic.getLawyer().getName());
                law_office.setText(lawyerBasic.getLawyer().getOfficeName());
                Glide.with(TalkingActivity.this).load(lawyerBasic.getLawyer().getHeadURL())
                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar)
                        .into(law_img);
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };
        L.d("=====TalkingAct", lawyerId);
        retrofitUtil.getLawyerBasic(Integer.parseInt(lawyerId), new ProgressSubscriber<LawyerBasic>(listener, TalkingActivity.this, false));

        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(ComplaintLawyerActivity.class, "lawyerId", lawyerId+"", "sid", userServiceId);
            }
        });
    }

    private void getAskDetail() {
        rlv_talking.setVisibility(View.VISIBLE);
        ll_bottom_menu.setVisibility(View.GONE);
        ll_input_send.setVisibility(View.GONE);
        getList.getConversationList();
    }


    private void hideMenu() {
        if (menu != null)
            menu.getItem(0).setVisible(false);
    }

    private void showMenu() {
        if (menu != null)
            menu.getItem(0).setVisible(true);
    }
    private void showShareMenu() {
        if (menu != null) {
            L.d("showShareMenu: ");
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_eva, this.menu);
        this.menu.getItem(0).setVisible(false);
        MenuItem item = this.menu.findItem(R.id.law_share);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShare();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.evaluate) {
            Intent intent = new Intent(TalkingActivity.this, EvaluateLawyerActivity.class);
            intent.putExtra("userServiceId", userServiceId);
            intent.putExtra("lawyerId", lawyerId);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    private class TalkingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<ConversationList.ConversationListBean> list = new ArrayList<>();

        public void setList(List<ConversationList.ConversationListBean> listBean) {
            list = listBean;
        }


        @Override
        public int getItemViewType(int position) {

            if (position == 0) {
                return R.layout.cell_load_more;
            } else if (list.get(position).getFrom() == LEFT) {
                return R.layout.cell_talk_left;
            } else {
                return R.layout.cell_talk_right;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            if (viewType == R.layout.cell_load_more) {
                return new MoreViewHolder(view);
            } else {
                return new ViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            if (position == 0) {
                if (hasMore) {
                    ((MoreViewHolder) holder).progress_bar_more.setVisibility(View.GONE);
                    ((MoreViewHolder) holder).tv_progress_more.setVisibility(View.VISIBLE);
                    ((MoreViewHolder) holder).tv_progress_more.setText("   ");
                    if (isAskDetail) ((MoreViewHolder) holder).detail_tv.setVisibility(View.GONE);
                } else {
                    ((MoreViewHolder) holder).progress_bar_more.setVisibility(View.GONE);
                    ((MoreViewHolder) holder).tv_progress_more.setVisibility(View.VISIBLE);
                    ((MoreViewHolder) holder).tv_progress_more.setText("已全部加载，没有更多消息了");
                    if (isAskDetail) ((MoreViewHolder) holder).detail_tv.setVisibility(View.VISIBLE);
                }

                if (list.size() < size) {
                    ((MoreViewHolder) holder).progress_bar_more.setVisibility(View.GONE);
                    ((MoreViewHolder) holder).tv_progress_more.setVisibility(View.VISIBLE);
                    ((MoreViewHolder) holder).tv_progress_more.setText("已全部加载，没有更多消息了");
                    if (isAskDetail) ((MoreViewHolder) holder).detail_tv.setVisibility(View.VISIBLE);
                }
            } else {
                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showShort(list.get(position).getContent());
                        L.d(list.get(position).getContent());
                    }
                });
                Glide.with(TalkingActivity.this).load(list.get(position).getHeadURL()).into(((ViewHolder) holder).civ_talking_avatar);
                if (list.get(position).getFrom() == LEFT)
                ((ViewHolder) holder).civ_talking_avatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TalkingActivity.this, LawyerDetailActivity.class);
                        intent.putExtra("lawyerId", Integer.parseInt(lawyerId));
                        startActivity(intent);
                    }
                });
                if (list.get(position).isIsPicture()) {
                    ((ViewHolder) holder).tv_talking_msg.setVisibility(View.GONE);
                    ((ViewHolder) holder).ll_iv_msg.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).iv_talking_msg.disenable();
                    Glide.with(TalkingActivity.this).load(list.get(position).getContent()).into(((ViewHolder) holder).iv_talking_msg);
//                Picasso.with(TalkingActivity.this).load(list.get(position).getContent()).into(holder.iv_talking_msg);

                    ((ViewHolder) holder).iv_talking_msg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PhotoView p = (PhotoView) view;
                            mInfo = p.getInfo();
                            Picasso.with(TalkingActivity.this).load(list.get(position).getContent()).into(mPhotoView);
                            mBg.startAnimation(in);
                            mBg.setVisibility(View.VISIBLE);
                            mParent.setVisibility(View.VISIBLE);
                            mPhotoView.animaFrom(mInfo);
                        }
                    });

                } else {
                    ((ViewHolder) holder).tv_talking_msg.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).ll_iv_msg.setVisibility(View.GONE);
                    ((ViewHolder) holder).tv_talking_msg.setText(list.get(position).getContent());
                }

                ((ViewHolder) holder).tv_time_conversation.setText(list.get(position).getTime());
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private CircleImageView civ_talking_avatar;
            private TextView tv_talking_msg, tv_time_conversation;
            private PhotoView iv_talking_msg;
            private LinearLayout ll_iv_msg;

            public ViewHolder(View itemView) {
                super(itemView);
                ll_iv_msg = (LinearLayout) itemView.findViewById(R.id.ll_iv_msg);
                civ_talking_avatar = (CircleImageView) itemView.findViewById(R.id.civ_talking_avatar);
                tv_talking_msg = (TextView) itemView.findViewById(R.id.tv_talking_msg);
                iv_talking_msg = (PhotoView) itemView.findViewById(R.id.iv_talking_msg);
                tv_time_conversation = (TextView) itemView.findViewById(R.id.tv_time_conversation);
            }
        }

        public class MoreViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_progress_more, detail_tv;
            private ProgressBar progress_bar_more;

            public MoreViewHolder(View itemView) {
                super(itemView);
                tv_progress_more = (TextView) itemView.findViewById(R.id.tv_progress_more);
                detail_tv = (TextView) itemView.findViewById(R.id.detail_tv);
                progress_bar_more = (ProgressBar) itemView.findViewById(R.id.progress_bar_more);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                String photo = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS).get(0);
                goLuban(photo);
            }
        }


    }

    private ProgressDialog dialog;

    private void goLuban(String photo) {
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在发送...");
        dialog.setCancelable(false);
        dialog.show();

        File file = new File(photo);


        L.d("y==", photo);

        Luban.get(this)
                .load(file)                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {

                        Map<String, RequestBody> map = new HashMap<>();
                        map.clear();
                        map.put("userId", RequestBody.create(null, "2"));
                        map.put("chatId", RequestBody.create(null, cid));
                        String key = "picture\"; filename=\"picture";
                        map.put(key, RequestBody.create(MediaType.parse("multipart/form-data"), file));


                        SubscriberOnNextListener<SendMsg> listener = new SubscriberOnNextListener<SendMsg>() {
                            @Override
                            public void onNext(SendMsg msg) {
                                if (dialog.isShowing()) dialog.dismiss();
                                initConversation("1");
                            }

                            @Override
                            public void onError(int code, String message) {
                                if (dialog.isShowing()) dialog.dismiss();
                                showShort(message);
                            }
                        };
                        retrofitUtil.getSendPic(map, new ProgressSubscriber<SendMsg>(listener, TalkingActivity.this, false));
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                }).launch();    //启动压缩
    }

    @Override
    public void onBackPressed() {
        if (mParent.getVisibility() == View.VISIBLE) {
            mBg.startAnimation(out);
            mPhotoView.animaTo(mInfo, new Runnable() {
                @Override
                public void run() {
                    mParent.setVisibility(View.GONE);
                }
            });
        } else {
            super.onBackPressed();
        }
    }


    //跳出选项框
    public PopupWindow getPopwindow(View view) {
        PopupWindow popupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.6f;
        getWindow().setAttributes(layoutParams);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(rl_give_money, Gravity.BOTTOM, 0, 0);
//        popupWindow.showAsDropDown(rlGiveMoney);
//        popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popupWindow.update();
        popupWindow.setTouchable(true);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.alpha = 1f;
                getWindow().setAttributes(layoutParams);
            }
        });
        return popupWindow;
    }


}