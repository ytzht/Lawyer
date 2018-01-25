package com.onekeyask.lawyer.ui.act.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.MyLawyerList;
import com.onekeyask.lawyer.entity.UserDiscoveries;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseActivity;
import com.onekeyask.lawyer.ui.act.lawyer.AskDetailActivity;
import com.onekeyask.lawyer.ui.act.lawyer.FindLawyerActivity;
import com.onekeyask.lawyer.ui.act.lawyer.LawyerDetailActivity;
import com.onekeyask.lawyer.utils.UserService;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchContentActivity extends BaseActivity {
    private TextView searchcancel;
    private EditText searchet;
    private ImageView ivclearhistory;
    private RecyclerView rlvhistory;
    private TextView historymore;
    private RecyclerView abouthistory;
    private TextView morelawyer;
    private RecyclerView aboutlawyer;
    private LinearLayout ll_history, ll_about, ll_lawyer;
    private UserService service;
    private String keyword = "";
    private String sp_history;
    private RelativeLayout rl_no_content;
    private List<String> history_list = new ArrayList<>();
    private List<UserDiscoveries.DataBean.UserDiscoveriesBean> discoverData = new ArrayList();
    private List<MyLawyerList.DataBean.LawyerListBean> lawyerList = new ArrayList<>();
    private SearchHistoryAdapter historyAdapter;
    private AboutAdapter aboutAdapter;
    private LawyerAdapter lawyerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_content);

        initView();

    }

    private void initView() {
        rl_no_content = (RelativeLayout) findViewById(R.id.rl_no_content);

        history_list.clear();
        ll_history = (LinearLayout) findViewById(R.id.ll_history);
        ll_about = (LinearLayout) findViewById(R.id.ll_about);
        ll_lawyer = (LinearLayout) findViewById(R.id.ll_lawyer);

        searching();

        service = new UserService(getBaseContext());

        sp_history = service.getSearchHistory();

        String[] split = sp_history.split("&");
        if (split.length > 0) {
            for (int i = split.length; i > 0; i--) {
                if (split.length != 1)
                history_list.add(split[i - 1]);
            }
        }
        this.rlvhistory = (RecyclerView) findViewById(R.id.rlv_history);
        this.abouthistory = (RecyclerView) findViewById(R.id.about_history);
        this.aboutlawyer = (RecyclerView) findViewById(R.id.about_lawyer);

        rlvhistory.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        abouthistory.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        aboutlawyer.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        historyAdapter = new SearchHistoryAdapter();
        rlvhistory.setAdapter(historyAdapter);


        this.searchcancel = (TextView) findViewById(R.id.search_cancel);
        searchcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.ivclearhistory = (ImageView) findViewById(R.id.iv_clear_history);
        this.morelawyer = (TextView) findViewById(R.id.more_lawyer);
        this.historymore = (TextView) findViewById(R.id.history_more);

        ivclearhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.saveSearchHistory("");
                history_list.clear();
                historyAdapter.notifyDataSetChanged();
            }
        });
        morelawyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FindLawyerActivity.class, "keyword", keyword);
            }
        });
        historymore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DiscoverSearchActivity.class, "keyword", keyword);
            }
        });


        this.searchet = (EditText) findViewById(R.id.search_et);
        searchet.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) searchet.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //实现自己的搜索逻辑

                    keyword = searchet.getText().toString();
                    searchet.setText(keyword);
                    sp_history = service.getSearchHistory();
                    if (!keyword.equals("")) {
                        if (TextUtils.isEmpty(sp_history)) {
                            service.saveSearchHistory(keyword);
                            history_list.add(0, keyword);
                        } else {
                            sp_history = service.getSearchHistory();
                            String[] split = sp_history.split("&");
                            boolean hasHis = false;
                            for (int i = 0; i < split.length; i++) {
                                if (split[i].equals(keyword)) {
                                    hasHis = true;
                                }
                            }
                            if (!hasHis) {
                                service.saveSearchHistory(sp_history + "&" + keyword);
                                history_list.add(0, keyword);
                            }
                        }
                        historyAdapter.notifyDataSetChanged();
//                        searchet.setText("");
                        searchOver();
                        startSearchContent(keyword);
                    }
                    return true;
                }
                return false;
            }
        });

        //监听软键盘是否显示或隐藏
        searchet.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        searchet.getWindowVisibleDisplayFrame(r);
                        int screenHeight = searchet.getRootView()
                                .getHeight();
                        int heightDifference = screenHeight - (r.bottom);
                        if (heightDifference > 200) {
                            //软键盘显示
                            searching();
                        } else {
                            //软键盘隐藏
                            searchOver();
                            if (discoverData.size() == 0 && lawyerList.size() == 0) {
                                ll_about.setVisibility(View.GONE);
                                ll_lawyer.setVisibility(View.GONE);
                                rl_no_content.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                });

    }

    private void searching() {
        ll_history.setVisibility(View.VISIBLE);
        ll_about.setVisibility(View.GONE);
        ll_lawyer.setVisibility(View.GONE);
        rl_no_content.setVisibility(View.GONE);
    }

    private void searchOver() {
        ll_history.setVisibility(View.GONE);
        if (discoverData.size() > 0)
            ll_about.setVisibility(View.VISIBLE);
        if (lawyerList.size() > 0)
            ll_lawyer.setVisibility(View.VISIBLE);
        rl_no_content.setVisibility(View.GONE);
    }

    private class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_search_history, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.history_content.setText(history_list.get(position));
            holder.history_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    history_list.remove(position);
                    String now_history = "";
                    for (int i = 0; i < history_list.size(); i++) {
                        if (i == history_list.size() - 1) {
                            now_history += history_list.get(i);
                        } else {
                            now_history += (history_list.get(i) + "&");
                        }
                    }
                    service.saveSearchHistory(now_history);
                    notifyDataSetChanged();
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((InputMethodManager) searchet.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    startSearchContent(history_list.get(position));
                    keyword = history_list.get(position);
                    searchet.setText(keyword);
                }
            });
        }

        @Override
        public int getItemCount() {
            return history_list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView history_content;
            private ImageView history_close;

            public ViewHolder(View itemView) {
                super(itemView);
                history_close = (ImageView) itemView.findViewById(R.id.history_close);
                history_content = (TextView) itemView.findViewById(R.id.history_content);
            }
        }
    }

    private void startSearchContent(final String content) {
        searchOver();
//        showShort("搜索 " + content);

        OkGo.<String>post(Apis.Discovery)
                .params("userId", service.getUserId())
                .params("keywords", content)
                .params("page", 1)
                .params("size", 10)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        UserDiscoveries discoveries = (new Gson()).fromJson(response.body(), UserDiscoveries.class);
                        if (discoveries.getCode() == 0) {
                            discoverData.clear();
                            discoverData.addAll(discoveries.getData().getUserDiscoveries());
                            aboutAdapter = new AboutAdapter();
                            abouthistory.setAdapter(aboutAdapter);
                            if (discoverData.size() == 0) {
                                ll_about.setVisibility(View.GONE);
                            } else {
                                ll_about.setVisibility(View.VISIBLE);
                            }

                            searchLaw(content);
                        } else {
                            showShort(discoveries.getMsg());
                        }
                    }
                });

    }

    private void searchLaw(String content) {
        OkGo.<String>post(Apis.LawyerList)
                .params("userId", service.getUserId())
                .params("page", 1)
                .params("size", 10)
                .params("searchKey", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MyLawyerList list = (new Gson()).fromJson(response.body(), MyLawyerList.class);
                        if (list.getCode() == 0) {

                            lawyerList = list.getData().getLawyerList();
                            lawyerAdapter = new LawyerAdapter();
                            aboutlawyer.setAdapter(lawyerAdapter);
                            if (lawyerList.size() == 0) {
                                ll_lawyer.setVisibility(View.GONE);
                            } else {
                                ll_lawyer.setVisibility(View.VISIBLE);
                            }

                            if (lawyerList.size() == 0 && discoverData.size() == 0) {

                                rl_no_content.setVisibility(View.VISIBLE);
                            }
                        } else {
                            showShort(list.getMsg());
                        }
                    }
                });
    }

    private class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_about_search, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {


            String s = discoverData.get(position).getContent().replace(keyword, "<font color='#f79f0a'>" + keyword + "</font>");
            holder.history_content.setText(Html.fromHtml(s));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchContentActivity.this, AskDetailActivity.class);
                    intent.putExtra("cid", discoverData.get(position).getChatId());
                    intent.putExtra("lawyerName", discoverData.get(position).getLawyerName());
                    intent.putExtra("officeName", discoverData.get(position).getOfficeName());
                    intent.putExtra("headUrl", discoverData.get(position).getHeadURL());
                    intent.putExtra("sid", discoverData.get(position).getUserServiceId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return discoverData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView history_content;

            public ViewHolder(View itemView) {
                super(itemView);
                history_content = (TextView) itemView.findViewById(R.id.history_content);
            }
        }
    }

    private class LawyerAdapter extends RecyclerView.Adapter<LawyerAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_my_lawyer_list, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            Glide.with(getBaseContext()).load(lawyerList.get(position).getHeadURL())
                    .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar)
                    .into(holder.find_civ);
            holder.find_office.setText(lawyerList.get(position).getLawyerOfficeName());

            String s = "";
            for (int i = 0; i < lawyerList.get(position).getSpecial().size(); i++) {
                s = s + lawyerList.get(position).getSpecial().get(i) + "、";
            }
            if (!s.equals("")) {
                s = s.substring(0, s.length() - 1);
            }
            holder.find_special.setText(s);

            if (lawyerList.get(position).getTags() != null) {
                if (lawyerList.get(position).getTags().size() == 1) {
                    holder.law_tag1.setVisibility(View.VISIBLE);
                    holder.law_tag2.setVisibility(View.GONE);
                    holder.law_tag3.setVisibility(View.GONE);
                    holder.law_tag1.setText(lawyerList.get(position).getTags().get(0));
                } else if (lawyerList.get(position).getTags().size() == 2) {
                    holder.law_tag1.setVisibility(View.VISIBLE);
                    holder.law_tag2.setVisibility(View.VISIBLE);
                    holder.law_tag3.setVisibility(View.GONE);
                    holder.law_tag1.setText(lawyerList.get(position).getTags().get(0));
                    holder.law_tag2.setText(lawyerList.get(position).getTags().get(1));
                } else if (lawyerList.get(position).getTags().size() > 2) {
                    holder.law_tag1.setVisibility(View.VISIBLE);
                    holder.law_tag2.setVisibility(View.VISIBLE);
                    holder.law_tag3.setVisibility(View.VISIBLE);
                    holder.law_tag1.setText(lawyerList.get(position).getTags().get(0));
                    holder.law_tag2.setText(lawyerList.get(position).getTags().get(1));
                    holder.law_tag3.setText(lawyerList.get(position).getTags().get(2));
                }
            } else {
                holder.law_tag1.setVisibility(View.GONE);
                holder.law_tag2.setVisibility(View.GONE);
                holder.law_tag3.setVisibility(View.GONE);
            }
            holder.find_num.setText(lawyerList.get(position).getFavoriteNum() + "人购买");
            holder.find_name.setText(lawyerList.get(position).getName());
            if (lawyerList.get(position).getTelPrice() > lawyerList.get(position).getTextPrice()) {
                holder.find_price.setText(lawyerList.get(position).getTextPrice() + "");
            } else {
                holder.find_price.setText("￥" + lawyerList.get(position).getTelPrice() + "元起");
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchContentActivity.this, LawyerDetailActivity.class);
                    intent.putExtra("lawyerId", lawyerList.get(position).getLawyerId());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return lawyerList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            CircleImageView find_civ;
            TextView find_name, find_office, find_special, law_tag1, law_tag2, law_tag3, find_num, find_price;

            public ViewHolder(View itemView) {
                super(itemView);
                find_civ = (CircleImageView) itemView.findViewById(R.id.find_civ);
                find_office = (TextView) itemView.findViewById(R.id.find_office);
                find_special = (TextView) itemView.findViewById(R.id.find_special);
                law_tag1 = (TextView) itemView.findViewById(R.id.law_tag1);
                law_tag2 = (TextView) itemView.findViewById(R.id.law_tag2);
                law_tag3 = (TextView) itemView.findViewById(R.id.law_tag3);
                find_num = (TextView) itemView.findViewById(R.id.find_num);
                find_price = (TextView) itemView.findViewById(R.id.find_price);
                find_name = (TextView) itemView.findViewById(R.id.find_name);
            }
        }
    }
}
