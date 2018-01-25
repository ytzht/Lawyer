package com.onekeyask.lawfirm.ui.act.user;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.CityList;
import com.onekeyask.lawfirm.entity.GotoVerify;
import com.onekeyask.lawfirm.entity.ProvinceBean;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseEvent;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.global.L;
import com.onekeyask.lawfirm.http.ProgressSubscriber;
import com.onekeyask.lawfirm.http.SubscriberOnNextListener;
import com.onekeyask.lawfirm.utils.AssetsUtils;
import com.onekeyask.lawfirm.utils.UserService;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


public class IdentityVerificationActivity extends BaseToolBarActivity {

    private EditText etname;
    private TextView tvarea;
    private LinearLayout llarea;
    private LinearLayout ll_sex;
    private EditText etoffice;
    private EditText etphone;
    private ImageView iv_example;

    private TextView nextsubmit;
    private TextView tv_sex;


    private UserService service;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private PopupWindow popupWindowSex = null;
    private View popupViewSex;
    private TextView tv_cancel_popup_sex;
    private TextView tv_yes_popup_sex;
    private TextView tv_boy;
    private TextView tv_girl;
    private ImageView img_add;
    private boolean isEdit = false;
    private String LisencePicURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_verification);

        setToolbarText("身份审核");

        service = new UserService(getBaseContext());

        initView();



        initClick();

        getOptionData();
    }


    private String sex = "";
    private String selectSex;
    private void initClick() {
        selectSex = "1";
        tv_boy.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv_girl.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));

        llarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initOptionPicker();

            }
        });

        ll_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowSex = getPopwindow(popupViewSex);
            }
        });
        nextsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInfo();
            }
        });

        tv_boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSex = "1";
                tv_boy.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
                tv_girl.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
            }
        });
        tv_girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSex = "2";
                tv_boy.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
                tv_girl.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
            }
        });
        tv_cancel_popup_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowSex.dismiss();
            }
        });
        tv_yes_popup_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowSex.dismiss();
                sex = selectSex;
                if (sex.equals("1")){
                    tv_sex.setText("男");
                }else {
                    tv_sex.setText("女");
                }

            }
        });
        iv_example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(IdentityVerificationActivity.this).create();

                alertDialog.show();

                Window window = alertDialog.getWindow();
                window.setContentView(R.layout.dialog_example);
            }
        });

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (files == null){
                    //打开图片选择器
                    PhotoPicker.builder()
                            .setPhotoCount(1)
                            .setShowCamera(true)
                            .setShowGif(false)
                            .setPreviewEnabled(true)
                            .start(IdentityVerificationActivity.this, PhotoPicker.REQUEST_CODE);
                } else{
                    //将 files 传入详情页
                    startActivity(IdentityVerificationNextActivity.class);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        files = null;
    }

    private void goSubmit() {
        service = UserService.service(getBaseContext());
        if (files == null) {
            showShort("请添加律师资格证照片");
            return;
        }

//        if (isEdit){
//            OkGo.<String>post(Apis.ModifyReviewInfo)
//                    .params("lawyerId", service.getLawyerId())
//                    .params("lawyerName", lawyerName)
//                    .params("sex", sex)
//                    .params("city", city)
//                    .params("district", district)
//                    .params("lisencePic", files)
//                    .params("lawyerOfficeName", lawyerOfficeName)
//                    .params("lawyerOfficeTel", lawyerOfficeTel)
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onSuccess(Response<String> response) {
//                            ResultData pic = (new Gson()).fromJson(response.body(), ResultData.class);
//                            if (pic.getCode() == 0) {
//                                showShort("提交成功");
//                                finish();
//                                EventBus.getDefault().post(BaseEvent.event(BaseEvent.FINISH_Identity));
//                                startActivity(GotoVerifyActivity.class);
//                            } else if (pic.getCode() == -107){
//                                showShort("已经提交过审核");
//                            }else {
//                                showShort(pic.getMsg());
//                            }
//                        }
//
//                        @Override
//                        public void onError(Response<String> response) {
//                            super.onError(response);
//                        }
//                    });
//        }else
        {
            Map<String, RequestBody> map = new HashMap<>();
            map.clear();
            map.put("lawyerId", RequestBody.create(null, UserService.service(getBaseContext()).getLawyerId() + ""));
            map.put("lawyerOfficeName", RequestBody.create(null, etoffice.getText().toString()));
            if (!etphone.getText().toString().equals(""))
                map.put("lawyerOfficeTel", RequestBody.create(null, etphone.getText().toString()));
            map.put("city", RequestBody.create(null, city));
            map.put("district", RequestBody.create(null, district));
            map.put("sex", RequestBody.create(null, sex));
            map.put("token", RequestBody.create(null, UserService.service(getBaseContext()).getToken()));
            map.put("lawyerName", RequestBody.create(null, etname.getText().toString()));
            String key = "lisencePic\"; filename=\"lisencePic";
            map.put(key, RequestBody.create(MediaType.parse("multipart/form-data"), files));

            SubscriberOnNextListener<ResultData> listener = new SubscriberOnNextListener<ResultData>() {
                @Override
                public void onNext(ResultData result) {

                    UserService.service(getBaseContext()).setOfficeName(etoffice.getText().toString());
                    if (result.getCode() == 0) {
                        finish();
                        EventBus.getDefault().post(BaseEvent.event(BaseEvent.FINISH_Identity));
                        startActivity(GotoVerifyActivity.class);
                    } else if (result.getCode() == -101) {
                        showShort("已经提交过审核");
                    } else {
                        showShort(result.getMsg());
                    }


                }

                @Override
                public void onError(int code, String message) {
                    showShort(message);
                }
            };
            retrofitUtil.gotoVerify(map, new ProgressSubscriber<ResultData>(listener, IdentityVerificationActivity.this, true));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (files != null){
            Glide.with(getBaseContext()).load(files).into(img_add);
        }
    }

    private void checkInfo() {

        if (etname.getText().toString().equals("")) {
            showShort("姓名不能为空");
            return;
        }

        if (etoffice.getText().toString().equals("")) {
            showShort("律所名不能为空");
            return;
        }

        if (tvarea.getText().toString().equals("")) {
            showShort("请选择所在地区");
            return;
        }

        if (!etphone.getText().toString().equals("") & etphone.getText().toString().length() != 11) {
            showShort("请输入正确的电话号码");
            return;
        }

        if (tv_sex.getText().toString().equals("")){
            showShort("请选择性别");
            return;
        }


//        Intent intent = new Intent(IdentityVerificationActivity.this, IdentityVerificationNextActivity.class);
//        intent.putExtra("lawyerOfficeName", etoffice.getText().toString());
//        intent.putExtra("lawyerOfficeTel", etphone.getText().toString());
//        intent.putExtra("city", city);
//        intent.putExtra("sex", sex);
//        if (isEdit) {
//            intent.putExtra("isEdit", LisencePicURL);
//        }
//        intent.putExtra("district", district);
//        intent.putExtra("lawyerName", etname.getText().toString());
//        startActivity(intent);


        goSubmit();
    }

    private void initView() {
        this.nextsubmit = (TextView) findViewById(R.id.next_submit);

        this.etphone = (EditText) findViewById(R.id.et_phone);
        this.iv_example = (ImageView) findViewById(R.id.iv_example);
        this.etoffice = (EditText) findViewById(R.id.et_office);
        this.llarea = (LinearLayout) findViewById(R.id.ll_area);
        this.ll_sex = (LinearLayout) findViewById(R.id.ll_sex);
        this.tvarea = (TextView) findViewById(R.id.tv_area);
        this.tv_sex = (TextView) findViewById(R.id.tv_sex);
        this.etname = (EditText) findViewById(R.id.et_name);
        this.img_add = (ImageView) findViewById(R.id.img_add);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在设置...");

        popupViewSex = LayoutInflater.from(this).inflate(R.layout.popup_change_sex, null);
        tv_cancel_popup_sex = (TextView) popupViewSex.findViewById(R.id.tv_cancel_popup);
        tv_yes_popup_sex = (TextView) popupViewSex.findViewById(R.id.tv_yes_popup);
        tv_boy = (TextView) popupViewSex.findViewById(R.id.tv_boy);
        tv_girl = (TextView) popupViewSex.findViewById(R.id.tv_girl);


        if(getIntent().hasExtra("un")){
            isEdit = true;
            loadData();
        }
    }
    private void loadPic() {
//        LisencePicURL = getIntent().getStringExtra("isEdit");
        new Thread(new Runnable() {
            @Override
            public void run() {
                saveImage(getBitmap(LisencePicURL), (new Date()).getTime());
            }
        }).start();
    }
    private void loadData() {


        OkGo.<String>get(Apis.GotoVerify).params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        GotoVerify info = (new Gson()).fromJson(response.body(), GotoVerify.class);
                        if (info.getCode() == 0) {

                            etname.setText(info.getData().getLawyer().getName());
                            if (info.getData().getLawyer().getSex() == 1) {
                                sex = "1";
                                tv_sex.setText("男");
                                tv_boy.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
                                tv_girl.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
                            } else {
                                sex = "2";
                                tv_sex.setText("女");
                                tv_boy.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
                                tv_girl.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
                            }

                            selectSex = sex;
                            etphone.setText(info.getData().getLawyerReviewInfo().getLawyerOfficeTel());
                            etoffice.setText(info.getData().getLawyerReviewInfo().getLawyerOfficeName());
//                            if (!info.getData().getLawyerReviewInfo().getStatus().equals("1")){
//                                complaint_over.setEnabled(false);
//                                complaint_over.setText("正在审核");
//                                complaint_over.setClickable(false);
//                                complaint_over.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.green_));
//                            }

                            tvarea.setText(info.getData().getLawyerReviewInfo().getCity() + "-" + info.getData().getLawyerReviewInfo().getDistrict());
                            city = info.getData().getLawyerReviewInfo().getCity();
                            district = info.getData().getLawyerReviewInfo().getDistrict();
                            LisencePicURL = info.getData().getLawyerReviewInfo().getLisencePicURL();
                            loadPic();
                        } else {
                            startActivity(LoginActivity.class);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        finish();
                        startActivity(LoginActivity.class);
                    }
                });
    }

    private ProgressDialog dialog;


    private String city = "", district = "";
    private void initOptionPicker() {//条件选择器初始化
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                city = options1Items.get(options1).getPickerViewText();
                district = options2Items.get(options1).get(options2);
                tvarea.setText(city + "-" + district);

            }
        })
                .setTitleText("所在地区选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .build();

        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
    }

    private void getOptionData() {

        String json = AssetsUtils.readText(getBaseContext(), "city.json");
        List<CityList> cityList = new Gson().fromJson(json, new TypeToken<ArrayList<CityList>>(){}.getType());
        for (int i = 0; i < cityList.size(); i++) {
            options1Items.add(new ProvinceBean(i, cityList.get(i).getAreaName(), "描述部分", "其他数据"));
            ArrayList<String> options2Items_0 = new ArrayList<>();
            for (int j = 0; j < cityList.get(i).getCities().size(); j++) {

                options2Items_0.add(cityList.get(i).getCities().get(j).getAreaName());
            }
            options2Items.add(options2Items_0);
        }
    }

    //跳出选项框
    @SuppressLint("WrongConstant")
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
        popupWindow.showAtLocation(ll_sex, Gravity.BOTTOM, 0, 0);
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

    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);
        if (event.getCode() == BaseEvent.FINISH_Identity){
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                String photoPath = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS).get(0);
                goLuban(photoPath);
            }
        }
    }


    public static File files;

    private void goLuban(String photoPath) {

        Luban.get(this)
                .load(new File(photoPath))                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        dialog.show();
                    }

                    @Override
                    public void onSuccess(File file) {
                        files = file;
                        if (dialog.isShowing()) dialog.dismiss();
                        Glide.with(getBaseContext()).load(files).into(img_add);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                }).launch();


    }



    public static final int IO_BUFFER_SIZE = 8 * 1024;

    public Bitmap getBitmap(String url) {
        Bitmap bitmap;
        InputStream in;
        L.d("url " + url);
        BufferedOutputStream out;
        try {
            in = new BufferedInputStream(new URL(url).openStream(),
                    IO_BUFFER_SIZE);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
            copy(in, out);
            out.flush();

            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            return bitmap;
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    public void saveImage(Bitmap bmp, long type) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "lisphoto");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "lisphoto" + type + ".jpg";

        final String path = Environment.getExternalStorageDirectory() + "/lisphoto/" + fileName;
        File file = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            L.d("saveImage: " + path);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    goLuban(path);
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
