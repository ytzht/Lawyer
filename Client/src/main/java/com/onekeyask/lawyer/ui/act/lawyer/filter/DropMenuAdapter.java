package com.onekeyask.lawyer.ui.act.lawyer.filter;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.baiiu.filter.adapter.MenuAdapter;
import com.baiiu.filter.adapter.SimpleExtTextAdapter;
import com.baiiu.filter.adapter.SimpleTextAdapter;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.baiiu.filter.interfaces.OnFilterItemClickListener;
import com.baiiu.filter.typeview.DoubleListView;
import com.baiiu.filter.typeview.SingleGridView;
import com.baiiu.filter.typeview.SingleListView;
import com.baiiu.filter.util.CommonUtil;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.CityList;
import com.onekeyask.lawyer.entity.SpecialBeanString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zht on 2017/08/09 17:01
 */

public class DropMenuAdapter implements MenuAdapter {
    private final Context mContext;
    private OnFilterDoneListener onFilterDoneListener;
    private String[] titles;
    private int special = -1;

    public DropMenuAdapter(Context context, String[] titles, OnFilterDoneListener onFilterDoneListener, int special) {
        this.mContext = context;
        this.titles = titles;
        this.special = special;
        this.onFilterDoneListener = onFilterDoneListener;
        hud = KProgressHUD.create(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
    }

    @Override
    public int getMenuCount() {
        return titles.length;
    }

    @Override
    public String getMenuTitle(int position) {
        return titles[position];
    }

    @Override
    public int getBottomMargin(int position) {
        if (position == 3) {
            return 0;
        }

        return UIUtil.dp(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);

        switch (position) {
            case 0:
                view = createSingleGridView();
                break;
            case 1:
                view = createDoubleListView();
                break;
            case 2:
                view = createSingleListView();
                break;
            case 3:
                // view = createDoubleGrid();
                view = createBetterDoubleGrid();
                break;
        }

        return view;
    }

    private View createSingleListView() {
        SingleListView<String> singleListView = new SingleListView<String>(mContext)
                .adapter(new SimpleExtTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String string) {
                        return string;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().singleListPosition = item;

                        FilterUrl.instance().position = 2;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();
                    }
                });

        List<String> list = new ArrayList<>();
        list.add("综合");
        list.add("评价等级");
        list.add("咨询人次");
        list.add("执业年限");
        list.add("价格由低到高");
        list.add("价格由高到低");
        singleListView.setList(list, -1);

        return singleListView;
    }
    KProgressHUD hud;

    private View createDoubleListView() {
        DoubleListView<FilterType, String> comTypeDoubleListView = new DoubleListView<FilterType, String>(mContext)
                .leftAdapter(new SimpleExtTextAdapter<FilterType>(null, mContext) {
                    @Override
                    public String provideText(FilterType filterType) {
                        return filterType.desc;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 44), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
                    }
                })
                .rightAdapter(new SimpleExtTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 30), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<FilterType, String>() {
                    @Override
                    public List<String> provideRightList(FilterType item, int position) {
                        List<String> child = item.child;
                        if (CommonUtil.isEmpty(child)) {
                            FilterUrl.instance().doubleListLeft = item.desc;
                            FilterUrl.instance().doubleListRight = "";

                            FilterUrl.instance().position = 0;
                            FilterUrl.instance().positionTitle = item.desc;

                            onFilterDone();
                        }

                        hud.show();
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                if (hud.isShowing()) hud.dismiss();
                            }
                        }, 500);




                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<FilterType, String>() {
                    @Override
                    public void onRightItemClick(FilterType item, String string) {
                        if (item.desc.equals("全国")) {
                            FilterUrl.instance().doubleListLeft = "";
                            FilterUrl.instance().doubleListRight = "";
                        } else {
                            FilterUrl.instance().doubleListLeft = item.desc;
                            FilterUrl.instance().doubleListRight = string;
                        }
                        FilterUrl.instance().position = 1;
                        FilterUrl.instance().positionTitle = string;

                        onFilterDone();
                    }
                });


        List<FilterType> list = new ArrayList<>();
        String json = AssetsUtils.readText(mContext, "city.json");
        List<CityList> cityList = new Gson().fromJson(json, new TypeToken<ArrayList<CityList>>() {
        }.getType());
        for (int i = 0; i < cityList.size(); i++) {
            FilterType filterType = new FilterType();
            filterType.desc = cityList.get(i).getAreaName();

            List<String> childList = new ArrayList<>();
            for (int j = 0; j < cityList.get(i).getCities().size(); j++) {
                childList.add(cityList.get(i).getCities().get(j).getAreaName());
            }
            filterType.child = childList;

            list.add(filterType);
        }

        //初始化选中.
        comTypeDoubleListView.setLeftList(list, 0);
        comTypeDoubleListView.setRightList(list.get(0).child, 0);
        comTypeDoubleListView.getLeftListView().setBackgroundColor(mContext.getResources().getColor(R.color.b_c_fafafa));

        return comTypeDoubleListView;
    }


    private View createSingleGridView() {
        SingleGridView<String> singleGridView = new SingleGridView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(0, UIUtil.dp(context, 6), 0, UIUtil.dp(context, 6));
                        checkedTextView.setGravity(Gravity.CENTER);
                        checkedTextView.setBackgroundResource(R.drawable.selector_filter_grid);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().singleGridPosition = item;

                        FilterUrl.instance().position = 0;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();

                    }
                });

        List<String> list = new ArrayList<>();
        for (int i = 0; i < SpecialBeanString.getSpecial().size(); i++) {
            list.add(SpecialBeanString.getSpecial().get(i).getName());
        }

//        list.add("全部");
//        list.add("合同纠纷");
//        list.add("房产纠纷");
//        list.add("婚姻继承");
//        list.add("人身损害");
//        list.add("劳动争议");
//        list.add("债权债务");
//        list.add("侵权纠纷");
//        list.add("消费维权");
//        list.add("交通事故");
//        list.add("刑事辩护");
//        list.add("投资");
//        list.add("融资");
//        list.add("兼并收购");
//        list.add("上市");
//        list.add("知识产权");
//        list.add("新三板");

        singleGridView.setList(list, special);


        return singleGridView;
    }


    private View createBetterDoubleGrid() {

        List<String> phases = new ArrayList<>();
        phases.add("私人律师");
        phases.add("图文咨询");
        phases.add("电话咨询");

        List<String> mides = new ArrayList<>();
        mides.add("0-10");
        mides.add("11-30");
        mides.add("31-51");
        mides.add("51以上");

        List<String> areas = new ArrayList<>();
        areas.add("1-3年");
        areas.add("3-5年");
        areas.add("5-10年");
        areas.add("10年以上");


        return new BetterDoubleGridView(mContext)
                .setmTopGridData(phases)
                .setmMidGridData(mides)
                .setmBottomGridList(areas)
                .setOnFilterDoneListener(onFilterDoneListener)
                .build();
    }


    private View createDoubleGrid() {
        DoubleGridView doubleGridView = new DoubleGridView(mContext);
        doubleGridView.setOnFilterDoneListener(onFilterDoneListener);


        List<String> phases = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            phases.add("3top" + i);
        }
        doubleGridView.setTopGridData(phases);

        List<String> areas = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            areas.add("3bottom" + i);
        }
        doubleGridView.setBottomGridList(areas);

        return doubleGridView;
    }


    private void onFilterDone() {
        if (onFilterDoneListener != null) {
            onFilterDoneListener.onFilterDone(0, "", "");
        }
    }
}
