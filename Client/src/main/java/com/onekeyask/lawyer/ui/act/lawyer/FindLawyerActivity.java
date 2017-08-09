package com.onekeyask.lawyer.ui.act.lawyer;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.ui.act.lawyer.filter.DropMenuAdapter;
import com.onekeyask.lawyer.ui.act.lawyer.filter.FilterUrl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindLawyerActivity extends BaseToolBarActivity implements OnFilterDoneListener {

    @BindView(R.id.search_view)
    ImageView searchView;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_et)
    TextView searchEt;
    @BindView(R.id.mFilterContentView)
    TextView mFilterContentView;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_lawyer);
        ButterKnife.bind(this);
        setToolbarText("找律师");

        initFilterDropDownView();
    }



    private void initFilterDropDownView() {
        String[] titleList = new String[]{"擅长领域", "区域", "排序", "筛选"};
        dropDownMenu.setMenuAdapter(new DropMenuAdapter(this, titleList, this));
    }

    @Override
    public void onFilterDone(int position, String positionTitle, String urlValue) {
        if (position != 3) {
            dropDownMenu.setPositionIndicatorText(FilterUrl.instance().position, FilterUrl.instance().positionTitle);
        }

        dropDownMenu.close();

        mFilterContentView.setText(FilterUrl.instance().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FilterUrl.instance().clear();
    }

}
