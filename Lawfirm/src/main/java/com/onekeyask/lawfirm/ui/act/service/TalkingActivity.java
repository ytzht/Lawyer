package com.onekeyask.lawfirm.ui.act.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.ConversationGetList;
import com.onekeyask.lawfirm.entity.SendCon;
import com.onekeyask.lawfirm.global.BaseActivity;
import com.onekeyask.lawfirm.global.BaseEvent;
import com.onekeyask.lawfirm.global.L;
import com.onekeyask.lawfirm.http.ProgressSubscriber;
import com.onekeyask.lawfirm.http.SubscriberOnNextListener;
import com.onekeyask.lawfirm.utils.DiffCallTalkingBack;
import com.onekeyask.lawfirm.utils.HideUtil;
import com.onekeyask.lawfirm.utils.photo.Info;
import com.onekeyask.lawfirm.utils.photo.PhotoView;
import com.squareup.picasso.Picasso;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

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

    private RecyclerView rlv_talking;
    private ImageView iv_input_bottom, iv_voice_bottom, iv_photo_bottom;
    private LinearLayoutManager mLinearLayoutManager;
    private TalkingAdapter talkingAdapter;
    private ConversationGetList getList;
    private long firstConversationId, lastConversationId, conversationId;
    private List<ConversationGetList.ConversationListBean> listBean;
    private TextView tv_send_msg;
    private EditText et_send_msg;
    private TextView tv_no_eva, tv_satisfied, tv_speak_eva, talk_toolbar_title;
    private Toolbar talk_toolbar;
    private int LEFT = 0;
    private int size = 10;
    private int orderId = 0;
    private String cid;
    private TagFlowLayout tag_flow;
    private LinearLayout ll_eva, ll_input_send, ll_bottom_menu;
    private boolean hasMore = true;
    private HandlerThread mCheckMsgThread;
    private Handler mCheckMsgHandler;
    private boolean isUpdateInfo;
    private Handler mHandler = new Handler();
    private boolean showInput = false;
    private static final int MSG_UPDATE_INFO = 0x110;

    private PhotoView mPhotoView;
    View mParent;
    View mBg;
    Info mInfo;
    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talking);
        initView();

        initPhotoView();

        //创建后台线程
        initBackThread();
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
        talk_toolbar_title = (TextView) findViewById(R.id.talk_toolbar_title);
        talk_toolbar_title.setText("聊天页面");
        talk_toolbar = (Toolbar) findViewById(R.id.talk_toolbar);
        setSupportActionBar(talk_toolbar);
        cid = getIntent().getStringExtra("chatId");
        tag_flow = (TagFlowLayout) findViewById(R.id.tag_flow);
        tv_no_eva = (TextView) findViewById(R.id.tv_no_eva);
        tv_satisfied = (TextView) findViewById(R.id.tv_satisfied);
        tv_speak_eva = (TextView) findViewById(R.id.tv_speak_eva);
        rlv_talking = (RecyclerView) findViewById(R.id.rlv_talking);
        ll_bottom_menu = (LinearLayout) findViewById(R.id.ll_bottom_menu);
        ll_input_send = (LinearLayout) findViewById(R.id.ll_input_send);
        ll_eva = (LinearLayout) findViewById(R.id.ll_eva);
        mLinearLayoutManager = new LinearLayoutManager(this);
        rlv_talking.setLayoutManager(mLinearLayoutManager);
        tv_send_msg = (TextView) findViewById(R.id.tv_send_msg);
        et_send_msg = (EditText) findViewById(R.id.et_send_msg);
        iv_input_bottom = (ImageView) findViewById(R.id.iv_input_bottom);
        iv_input_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (showInput){
//
//                    ll_input_send.setVisibility(View.VISIBLE);
//                    ll_bottom_menu.setVisibility(View.GONE);
//                    showInput = false;
//                }else {
//                    ll_input_send.setVisibility(View.GONE);
//                    ll_bottom_menu.setVisibility(View.VISIBLE);
//                    showInput = true;
//                }
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });
        iv_voice_bottom = (ImageView) findViewById(R.id.iv_voice_bottom);
        iv_photo_bottom = (ImageView) findViewById(R.id.iv_photo_bottom);


//        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view_img);
        // 启用图片缩放功能
//        photoView.enable();

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


        tv_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_send_msg.getText().toString())) {
                    sendMsg();
                }
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
    }

    private void sendMsg() {
        SubscriberOnNextListener listener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                et_send_msg.setText("");
                initConversation("1");
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getSendCon(3, cid, et_send_msg.getText().toString(), new ProgressSubscriber<SendCon>(listener, TalkingActivity.this, false));
    }


    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post(BaseEvent.event(BaseEvent.GO_SERVICE));
        //开始查询
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        mCheckMsgThread.quit();
    }

    private void initConversation(final String direction) {

        final SubscriberOnNextListener listener = new SubscriberOnNextListener<ConversationGetList>() {
            @Override
            public void onNext(ConversationGetList o) {
                getList = o;

                cid = String.valueOf(getList.getChatId());
                orderId = getList.getOrder().getOrderId();
                switch (getList.getStatus()) {
                    case "1"://进行中
                        isUpdateInfo = true;
                        L.d("进行中 订单信息 " + getList.getOrder().getOrderId());
                        break;
                    case "2"://已结束
                        isUpdateInfo = false;
                        L.d("已结束 订单信息 " + getList.getOrder().getOrderId());
                        if (getList.isEvaStatus()) {
                            //已评价
                            ll_eva.setVisibility(View.VISIBLE);
                            ll_input_send.setVisibility(View.GONE);
                            ll_bottom_menu.setVisibility(View.GONE);

                            tv_satisfied.setText(getList.getEvaluation().getScore());
                            tv_speak_eva.setText(getList.getEvaluation().getComment());

                            String[] mValues = new String[getList.getEvaluation().getTagList().size()];
                            for (int i = 0; i < getList.getEvaluation().getTagList().size(); i++) {
                                mValues[i] = getList.getEvaluation().getTagList().get(i).getTag();
                            }

                            tag_flow.setAdapter(new TagAdapter<String>(mValues) {
                                @Override
                                public View getView(FlowLayout parent, int position, String s) {
                                    TextView tv = (TextView) LayoutInflater.from(TalkingActivity.this).inflate(R.layout.tag_tv, tag_flow, false);
                                    tv.setText(s);
                                    return tv;
                                }
                            });


                        } else {
                            //未评价
                            tv_no_eva.setVisibility(View.VISIBLE);
                            ll_input_send.setVisibility(View.GONE);
                            ll_bottom_menu.setVisibility(View.GONE);
                        }
                        break;

                }

                getList.getFromType();//订单类型 1图文咨询 2免费提问

                ConversationGetList.ConversationListBean bean = new ConversationGetList.ConversationListBean
                        ((int) conversationId, 1, 1, "", "", "", true, "", 3);

                if (talkingAdapter != null) {
                    boolean b = (getList.getConversationList().size() > 0);//为true就是有新增数据
                    //改变第一个和最后一个对话的id
                    if (b) {//改变上限和下限的id
                        if (direction.equals("1")) {
                            //向下查数据改变最后一个最近发言的id
                            lastConversationId = getList.getConversationList().get(getList.getConversationList().size() - 1).getConversationId();
                        } else {
                            hasMore = getList.isHasMore();
                            L.d("direction=0 hasMore == " + hasMore);
                            firstConversationId = getList.getConversationList().get(0).getConversationId();
                        }
                        L.d("数据有改变了");

                        List<ConversationGetList.ConversationListBean> newList = new ArrayList<>();


                        if (direction.equals("1")) {
                            newList.addAll(listBean);
                            newList.addAll(getList.getConversationList());
                        } else {
                            newList.add(0, bean);
                            newList.addAll(getList.getConversationList());
                            List<ConversationGetList.ConversationListBean> newList1 = new ArrayList<>();
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
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        if (direction.equals("1")) {
            conversationId = lastConversationId;//说明是向下查数据
        } else {
            conversationId = firstConversationId;//向上查数据
        }

        retrofitUtil.getConversationList(3, cid, conversationId, direction, size, new ProgressSubscriber<ConversationGetList>(listener, TalkingActivity.this, false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_eva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.evaluate) {
//            startActivity(EvaluateLawyerActivity.class);
//            finish();
//            return true;
//        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    private class TalkingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<ConversationGetList.ConversationListBean> list = new ArrayList<>();


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
                } else {
                    ((MoreViewHolder) holder).progress_bar_more.setVisibility(View.GONE);
                    ((MoreViewHolder) holder).tv_progress_more.setVisibility(View.VISIBLE);
                    ((MoreViewHolder) holder).tv_progress_more.setText("已全部加载，没有更多消息了");
                }
                if (list.size() < size) {
                    ((MoreViewHolder) holder).progress_bar_more.setVisibility(View.GONE);
                    ((MoreViewHolder) holder).tv_progress_more.setVisibility(View.VISIBLE);
                    ((MoreViewHolder) holder).tv_progress_more.setText("已全部加载，没有更多消息了");
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

                if (list.get(position).isIsPicture()) {
                    ((ViewHolder) holder).tv_talking_msg.setVisibility(View.GONE);
                    ((ViewHolder) holder).ll_iv_msg.setVisibility(View.VISIBLE);
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

        public void setList(List<ConversationGetList.ConversationListBean> listBean) {
            list = listBean;
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

            private TextView tv_progress_more;
            private ProgressBar progress_bar_more;

            public MoreViewHolder(View itemView) {
                super(itemView);
                tv_progress_more = (TextView) itemView.findViewById(R.id.tv_progress_more);
                progress_bar_more = (ProgressBar) itemView.findViewById(R.id.progress_bar_more);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
                        map.put("lawyerId", RequestBody.create(null, "3"));
                        map.put("chatId", RequestBody.create(null, cid));
                        String key = "picture\"; filename=\"picture";
                        map.put(key, RequestBody.create(MediaType.parse("multipart/form-data"), file));

                        SubscriberOnNextListener<SendCon> listener = new SubscriberOnNextListener<SendCon>() {
                            @Override
                            public void onNext(SendCon msg) {
                                if (dialog.isShowing()) dialog.dismiss();
                                initConversation("1");
                            }

                            @Override
                            public void onError(int code, String message) {
                                if (dialog.isShowing()) dialog.dismiss();
                                showShort(message);
                            }
                        };
                        retrofitUtil.getSendPic(map, new ProgressSubscriber<SendCon>(listener, TalkingActivity.this, false));
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

}
