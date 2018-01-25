package com.onekeyask.lawfirm.ui.act.search;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.global.BaseActivity;
import com.onekeyask.lawfirm.utils.UserService;

import java.util.ArrayList;
import java.util.List;

public class SearchContentActivity extends BaseActivity {

    private TextView searchcancel;
    private EditText searchet;
    private ImageView ivclearhistory;
    private RecyclerView rlvhistory;
    private LinearLayout ll_history;
    private UserService service;
    private String keyword = "";
    private String sp_history;
    private RelativeLayout rl_no_content;
    private List<String> history_list = new ArrayList<>();
    private SearchHistoryAdapter historyAdapter;

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

        rlvhistory.setLayoutManager(new LinearLayoutManager(this) {
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

        ivclearhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.saveSearchHistory("");
                history_list.clear();
                historyAdapter.notifyDataSetChanged();
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
                        }
                    }

                });

    }


    private void searching() {
        ll_history.setVisibility(View.VISIBLE);
        rl_no_content.setVisibility(View.GONE);
    }

    private void searchOver() {
        ll_history.setVisibility(View.GONE);
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
        startActivity(SearchLawActivity.class, "content", content);
    }

}
