package com.onekeyask.lawyer.ui.act.lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.MyLawyerList;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.ui.act.lawyer.filter.DropMenuAdapter;
import com.onekeyask.lawyer.ui.act.lawyer.filter.FilterUrl;
import com.onekeyask.lawyer.utils.MyDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FindLawyerActivity extends BaseToolBarActivity implements OnFilterDoneListener {

    @BindView(R.id.search_view)
    ImageView searchView;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_et)
    TextView searchEt;
    @BindView(R.id.rlv_find)
    RecyclerView rlvFind;
    @BindView(R.id.mFilterContentView)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;

    private FindLawyerAdapter adapter;
    int page = 1;
    private boolean hasMore = true;
    private String url;
    private List<MyLawyerList.DataBean.LawyerListBean> lawyerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_lawyer);
        ButterKnife.bind(this);
        setToolbarText("找律师");

        String[] titleList = new String[]{"擅长领域", "区域", "排序", "筛选"};
        dropDownMenu.setMenuAdapter(new DropMenuAdapter(this, titleList, this));

        url = Apis.LawyerList;
//                + "?office="+getIntent().getStringExtra("position");

        initView();
    }

    private void initView() {

        refreshLayout.setRefreshHeader(new ClassicsHeader(getBaseContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getBaseContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(200);
                page = 1;
                initData();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(200);
                if (hasMore) {
                    page += 1;
                    initData();
                } else {
                    showShort("已加载全部数据");
                }
            }
        });

        rlvFind.setLayoutManager(new LinearLayoutManager(this));
        rlvFind.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        adapter = new FindLawyerAdapter();
        rlvFind.setAdapter(adapter);

        initData();

    }

    private void initData() {

        OkGo.<String>get(url)
                .params("userId", 2)
                .params("size", 4)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MyLawyerList list = (new Gson()).fromJson(response.body(), MyLawyerList.class);
                        if (list.getCode() == 0) {
                            hasMore = list.getData().isHasMore();
                            lawyerList = list.getData().getLawyerList();
                            adapter.notifyDataSetChanged();
                        } else {
                            showShort(list.getMsg());
                        }
                    }
                });


    }


    @Override
    public void onFilterDone(int position, String positionTitle, String urlValue) {
        if (position != 3) {
            dropDownMenu.setPositionIndicatorText(FilterUrl.instance().position, FilterUrl.instance().positionTitle);
        }

        dropDownMenu.close();

        if (!FilterUrl.instance().toString().equals("")) {
            url = Apis.MyLawyerList;
        } else {
            url = Apis.MyLawyerList + "?" + FilterUrl.instance().toString().substring(1, FilterUrl.instance().toString().length());
        }

        page = 1;
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FilterUrl.instance().clear();
    }


    private class FindLawyerAdapter extends RecyclerView.Adapter<FindLawyerAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_my_lawyer_list, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            Glide.with(getBaseContext()).load(lawyerList.get(position).getHeadURL()).into(holder.find_civ);
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
                    Intent intent = new Intent(FindLawyerActivity.this, LawyerDetailActivity.class);
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
